/**
 * Created by fre on 10/07/16.
 * Require PNOTIFY JS
 */

//Setting styling of PNotify to bootstrap3
PNotify.prototype.options.styling = "bootstrap3";


var showSuccess = function(title, text){
    _printNotify(title,text,'success');
};
var showInfo = function(title, text){
    _printNotify(title,text,'info');
};
var showError = function(title, text){
    _printNotify(title,text,'error');
};
var showNotification = function(title, text, type, time){
        new PNotify({
            title: title,
            text: text,
            type: type,
            delay: time
        });
}

    var _printNotify = function(title, text, type){
        new PNotify({
            title: title,
            text: text,
            type: type,
            delay: 3000
        });
    };

var getFieldAlert = function(message){
        return '<div class="alert form-error-div" >'+message+'</div>'
};
var getGeneralAlert = function(message){
        return '<div class="alert alert-danger form-general-div"><strong>'+message+'</strong></div>';
};
//HANDLE FORM PLUGIN CODED BY ME
//THIS FORM STUFF WILL BE HANDLED AS A JQUERY PLUGIN
(function ( $ ) {

    $.fn.handleForm = function(successFunction, options) {
        
        if (successFunction === undefined)
            successFunction = function(){
                alert("Form correctly submitted");
            };

        var defaults = {
            errorClass : 'bad',
            abnomalResponseFunction : function () {
                alert("Server did not answer with a 400 error. We got a problem");
            },
            errorFieldSelector : ".item.form-group",
            errorFunction : function(data){console.log("Got error response" + data);},
            debug : false
        };

        var options = $.extend( {}, defaults, options );

        if(options.debug)
            console.log("will handle form " + this);
        var form = this;

        //hang on event of form with id=myform
        $(form).submit(function(e) {
            //prevent Default functionality
            e.preventDefault();
            submitForm(form);
            return false;
        });

        var submitForm = function(){
                    if (options.debug)
                        console.log("Submitted form with id" + $(form).attr('id'));

                    //Remove the old validation error
                    if (options.debug)
                        console.log("Removing all the old validation error if present before posting a new request");
                    form.find(options.errorFieldSelector).removeClass(options.errorClass);
                    form.find(".alert.form-error-div").remove();
                    form.find(".alert-danger.form-general-div").remove();
                    //Choose how to submit the form
                    var formData = $(form).serialize();
                    var contentType = 'application/x-www-form-urlencoded; charset=UTF-8';
                    var processData = true;
                    //If the form has the enctype setted to multipart/form-data we need to change
                    //The way the form is handled by jquery
                    if ($(form).attr('enctype') === "multipart/form-data"){
                        formData = new FormData($(form)[0]);
                        contentType = false;
                        processData = false;
                        if (options.debug)
                            console.log("Posting a form with multipart/form-data");
                    }
                    //do your own request an handle the results
                    $.ajax({
                        url: $(form).attr('action'),
                        type: 'post',
                        //dataType: 'json',
                        data: formData,
                        processData: processData,
                        contentType: contentType,
                        success: function(data) {
                            successFunction(data);
                            // remove all the validation stuff
                            if (options.debug)
                                console.log("Removing all the validation error related classes and divs we got success baby");
                            form.find(options.errorFieldSelector).removeClass(options.errorClass);
                            form.find(".alert.form-error-div").remove();
                            form.find(".alert.alert-danger.form-general-div").remove();
                            // Do something
                            if (options.debug){
                                console.log("Here what we got in response");
                                console.log(data);
                            }
                        },
                        error: function(xhr, status, error){
                            if (xhr.status !== 400){
                                if(xhr.status === 401){
                                    showError("Error", "Your session has timed out ");
                                    return;
                                    }
                                if(xhr.status === 500){
                                    showError("Error", "Internal server error, if this happens again, please contact the Administrator");
                                    return;
                                    }
                                options.abnomalResponseFunction();
                                return;
                            }
                            if (options.debug)
                                console.log(xhr);
                            _.each(xhr.responseJSON, function (errors, field) {
                                if (errors.isEmpty)
                                    return;
                                var errorsString = _.reduce(errors, function(a,b) {return a + ", "+ b;});
                                var fieldSelector ="[name='"+field+"']";
                                if (!($(fieldSelector).length)){
                                    if (options.debug)
                                        console.log("Adding error as general error cause we can't find the field in the form " + field);
                                    $(form).prepend(getGeneralAlert(errorsString));
                                    return;
                                }
                                var formGroupItem = $(fieldSelector).closest(options.errorFieldSelector);
                                if (!formGroupItem.length){
                                    console.log("Error to find the field to which append error using closest with selector "
                                    + options.errorFieldSelector + " starting from " + $(fieldSelector) + " so the error will be added as a general error");
                                    $(form).prepend(getGeneralAlert(errorsString));
                                    return;
                                }
                                formGroupItem.addClass("bad");
                                if (options.debug)
                                    console.log("Appending error using fieldselector " + fieldSelector);
                                formGroupItem.append(getFieldAlert(errorsString));
                                //Call the error function
                                options.errorFunction(xhr.responseJSON);
                            });
                        }
                    });
        };

        return this;
    };


}( jQuery ));
