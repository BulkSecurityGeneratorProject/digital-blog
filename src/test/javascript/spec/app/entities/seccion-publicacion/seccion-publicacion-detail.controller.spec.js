'use strict';

describe('Controller Tests', function() {

    describe('SeccionPublicacion Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockSeccionPublicacion, MockSeccion, MockPublicacion;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockSeccionPublicacion = jasmine.createSpy('MockSeccionPublicacion');
            MockSeccion = jasmine.createSpy('MockSeccion');
            MockPublicacion = jasmine.createSpy('MockPublicacion');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'SeccionPublicacion': MockSeccionPublicacion,
                'Seccion': MockSeccion,
                'Publicacion': MockPublicacion
            };
            createController = function() {
                $injector.get('$controller')("SeccionPublicacionDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'digitalBlogApp:seccionPublicacionUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
