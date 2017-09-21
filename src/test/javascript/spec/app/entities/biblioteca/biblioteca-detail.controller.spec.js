'use strict';

describe('Controller Tests', function() {

    describe('Biblioteca Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockBiblioteca, MockUsuario, MockSeccion;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockBiblioteca = jasmine.createSpy('MockBiblioteca');
            MockUsuario = jasmine.createSpy('MockUsuario');
            MockSeccion = jasmine.createSpy('MockSeccion');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Biblioteca': MockBiblioteca,
                'Usuario': MockUsuario,
                'Seccion': MockSeccion
            };
            createController = function() {
                $injector.get('$controller')("BibliotecaDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'digitalBlogApp:bibliotecaUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
