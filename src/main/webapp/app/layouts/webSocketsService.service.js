(function () {

    'use strict';

    angular.module('digitalBlogApp').factory('webSocketService', webSocketService);
    webSocketService.$inject = ['$resource', '$http','Principal','$q','$window','AuthServerProvider','$rootScope'];

    function webSocketService($resource, $http,Principal,$q,$window,AuthServerProvider,$rootScope) {

        var stompClient = null;
        var subscriber = null;
        var listener = $q.defer();
        var connected = $q.defer();
        var alreadyConnectedOnce = false;
        var cuentaActual;
        var idUsuario;

        var service = {
            connect: connect,
            disconnect: disconnect,
            receive: receive,
            ///  sendActivity: sendActivity,
            subscribe: subscribe,
            unsubscribe: unsubscribe
        };

        return service;
        /**
         * @author Cristhopher Sullivan
         * se conecta al webSocket
         * se crea el url
         * @version 1.0
         */
        function connect(id) {
            if(alreadyConnectedOnce == false){
                /*building absolute path so that websocket doesnt fail when deploying with a context path*/
                var loc = $window.location;
                var url = '//' + loc.host + loc.pathname + 'websocket/DigitalBlog';
                var authToken = AuthServerProvider.getToken();
                if (authToken) {
                    url += '?access_token=' + authToken;
                }
                var socket = new SockJS(url);
                stompClient = Stomp.over(socket);
                var stateChangeStart;
                var headers = {};
                stompClient.connect(headers, function () {
                    connected.resolve('success');
                    subscribe(id);
                    alreadyConnectedOnce = true;
                    // sendActivity();
                    // if (!alreadyConnectedOnce) {
                    //     stateChangeStart = $rootScope.$on('$stateChangeStart', function () {
                    //         sendActivity();
                    //     });
                    //     alreadyConnectedOnce = true;
                    // }
                });
                $rootScope.$on('$destroy', function () {
                    if (angular.isDefined(stateChangeStart) && stateChangeStart !== null) {
                        stateChangeStart();
                    }
                });
            }

        }

        /**
         * @author Cristhopher Sullivan
         * Desconecta el socket
         * @version 1.0
         */
        function disconnect() {
            if (stompClient !== null) {
                stompClient.disconnect();
                stompClient = null;
                 alreadyConnectedOnce = false;
            }
        }
        function receive() {
            return listener.promise;
        }
        // function sendActivity() {
        //     if (stompClient !== null && stompClient.connected) {
        //         stompClient
        //             .send('/topic/WST',
        //                 {},
        //                 angular.toJson({'page': $rootScope.toState.name}));
        //     }
        // }
        /**
         * @author Cristhopher Sullivan
         * se subscribe al topic personal del usuario
         * @param usuarioId
         * @version 1.0
         */
        function subscribe(usuarioId) {
            connected.promise.then(function () {
                subscriber = stompClient.subscribe('/topic/' + usuarioId, function (data) {
                    listener.notify(angular.fromJson(data.body));
                });
            }, null, null);
        }
        function unsubscribe() {
            if (subscriber !== null) {
                subscriber.unsubscribe();
            }
            listener = $q.defer();
        }
    }

})();

