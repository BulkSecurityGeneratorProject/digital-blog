(function() {
    'use strict';

    angular
        .module('digitalBlogApp', [
            'ngStorage',
            'ngResource',
            'ngCookies',
            'ngAria',
            'ngAnimate',
            'ngCacheBuster',
            'ngFileUpload',
            'ui.bootstrap',
            'ui.bootstrap.datetimepicker',
            'ui.router',
            'infinite-scroll',
            'autocomplete',
            'toastr',
            'angular-loading-bar',
            'ui.tinymce',
            'angular-notification-icons',
            'ui.bootstrap.contextMenu',
            'ui.bootstrap.modal',
            'ngSanitize'
            // jhipster-needle-angularjs-add-module JHipster will add new module here


        ])
        .run(run);

    run.$inject = ['stateHandler'];

    function run(stateHandler) {
        stateHandler.initialize();
    }
})();
