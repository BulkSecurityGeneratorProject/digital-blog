(function() {
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
            'ui.router',
            'infinite-scroll',
            'angular-notification-icons',
            'ui.tinymce',
            // jhipster-needle-angularjs-add-module JHipster will add new module here
            'angular-loading-bar',
            'autocomplete',
            'toastr',
            'ngAnimate',
            'ui.bootstrap.contextMenu',
            'ui.bootstrap.modal',


        ])
        .run(run);

    run.$inject = ['stateHandler'];

    function run(stateHandler) {
        stateHandler.initialize();
    }
})();
