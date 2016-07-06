# Gentelella admin template for Play Framework

Starting from [play2-java](https://github.com/playframework/playframework/tree/master/templates/play-java) template
a minimal structure for the views and assets has been setted up in so developer can create new views easily.


## Installation
Being a Play2 java project the best way to install and work with it is using the [activator](https://playframework.com/download)

Once the activator is installed and added to the path simply type

```sh
$ activator run
```

to run it in development mode and

```sh
$ activator test
```
to test it

## View structure

In the views folder can be found two different templates that the user is going to extend
  - Skeleton that contains only the basics assets and html
  - Base that extends skeleton adding page components such as footer, side and top nav

For example a 404 page will use Skeleton while a view for a logged-in page will use Base

Feel free to run the project and browse the code for in-depth details

## License

This project is licensed under The MIT License (MIT).




