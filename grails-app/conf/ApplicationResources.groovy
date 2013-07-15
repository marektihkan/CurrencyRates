modules = {
    application {
        dependsOn 'utils'
        resource url:'js/application.js'
    }
    utils {
        dependsOn 'jquery'
        resource url:'js/spin.js'
        resource url:'js/jquery.timeago.js'
    }
}