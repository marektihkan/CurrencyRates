modules = {
    application {
        dependsOn 'utils'
        resource url:'js/application.js'
    }
    utils {
        resource url:'js/spin.js'
    }
}