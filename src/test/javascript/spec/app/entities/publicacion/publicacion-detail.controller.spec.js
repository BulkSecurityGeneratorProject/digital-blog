'use strict';

describe('Controller Tests', function() {

    describe('Publicacion Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockPublicacion, MockUsuario, MockCategoria, MockTema;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockPublicacion = jasmine.createSpy('MockPublicacion');
            MockUsuario = jasmine.createSpy('MockUsuario');
            MockCategoria = jasmine.createSpy('MockCategoria');
            MockTema = jasmine.createSpy('MockTema');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Publicacion': MockPublicacion,
                'Usuario': MockUsuario,
                'Categoria': MockCategoria,
                'Tema': MockTema
            };
            createController = function() {
                $injector.get('$controller')("PublicacionDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'digitalBlogApp:publicacionUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
