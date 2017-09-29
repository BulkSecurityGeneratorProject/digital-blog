(function () {
    'use strict';

    angular
        .module('digitalBlogApp', [
            'ngStorage',
            'ngResource',
            'ngCookies',
            'ngAria',
            'ngCacheBuster',
            'ngFileUpload',
            'ui.bootstrap',
            'ui.bootstrap.datetimepicker',
            'ui.bootstrap.contextMenu',
            'ui.router',
            'ui.tinymce',
            'infinite-scroll',
            'toastr',
            // jhipster-needle-angularjs-add-module JHipster will add new module here
            'angular-loading-bar',
            'angular-notification-icons'




        ])
        .run(run);

    run.$inject = ['stateHandler'];

    function run(stateHandler) {
        stateHandler.initialize();
    }
})();
