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
            // jhipster-needle-angularjs-add-module JHipster will add new module here
            'angular-loading-bar',
            'ui.tinymce',
            'autocomplete',
            'toastr',
            'ngAnimate',
            'ui.bootstrap.contextMenu',
            'ui.bootstrap.modal',
            'angular-notification-icons',

        ])
        .run(run);

    run.$inject = ['stateHandler'];

    function run(stateHandler) {
        stateHandler.initialize();
    }
})();
