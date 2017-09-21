'use strict';

describe('Controller Tests', function() {

    describe('Comentario Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockComentario, MockRespuesta, MockUsuario, MockPublicacion;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockComentario = jasmine.createSpy('MockComentario');
            MockRespuesta = jasmine.createSpy('MockRespuesta');
            MockUsuario = jasmine.createSpy('MockUsuario');
            MockPublicacion = jasmine.createSpy('MockPublicacion');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Comentario': MockComentario,
                'Respuesta': MockRespuesta,
                'Usuario': MockUsuario,
                'Publicacion': MockPublicacion
            };
            createController = function() {
                $injector.get('$controller')("ComentarioDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'digitalBlogApp:comentarioUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
