(function() {
    'use strict';

    angular
        .module('digitalBlogApp')
        .config(bootstrapMaterialDesignConfig);

    bootstrapMaterialDesignConfig.$inject = [];

    function bootstrapMaterialDesignConfig() {
        $.material.init();

    }
})();
