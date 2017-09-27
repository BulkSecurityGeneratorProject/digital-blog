(function() {
    'use strict';

    angular
        .module('digitalBlogApp')
        .controller('ListarNotificacionesController', ListarNotificacionesController);

    ListarNotificacionesController.$inject = ['$state','$scope', 'Principal','PersonalizarCuenta','ListarNotificaciones','PublicacionServiceShare','$uibModal','CompartidaServiceShare','$window'];
    function ListarNotificacionesController ($state,$scope, Principal,PersonalizarCuenta,ListarNotificaciones,PublicacionServiceShare,$uibModal,CompartidaServiceShare,$window) {
        var vm = this;
        vm.usuario;
        vm.mostrar = 0;
        vm.notificaciones=[];
        vm. users=[];
        vm.limiteNotificaciones=2;
        vm.limiteNotificacionesPublicaciones = 2;
        vm.notificacionesPublicaciones=[];
        vm.listaPublicaciones=[];
        vm.idPublicaciones=[];
        vm.listaNotificacionesPublicaciones=[];
        vm.notificacionesAmigos=[];
        vm.errorCon = false;
        vm.listaColaborativas = [];
        vm.publicacionesResponse=[];
        vm.escritor;
        vm.tipoLike = "Nuevo like a su publicación";

        obtenerUsuarioNormal();

        /**
         * @author Maureen Leon
         * Permite obtener la cuenta logueada actualmente y envia el id del JHIUSER para obtener el usuario
         * @version 1.0
         */
        function obtenerUsuarioNormal(){
            Principal.identity().then(function(account) {
                vm.account = account;
                PersonalizarCuenta.obtenerUsuarioNormal(account.id).success(function(response){
                    vm.usuario=response;
                }).error(function (error){
                    if(error.description == "Internal server error"){
                        vm.errorCon =  true;
                    }
                });
            });
        }

        /**
         * @author Maureen Leon
         * Obtiene las notificaciones correspondientes a un usuario se envia el id del usuario
         * @version 1.0
         */
        vm.getPublicaciones=function(){
            ListarNotificaciones.obtenerNotificaciones(vm.usuario.id).success(function(response){
                vm.notificaciones=response;
                if( vm.notificaciones.length!= 0){
                    obtenerUsuarios();
                }
            }).error(function (error){
            });
        }

        /**
         * @author Maureen Leon
         * Obtiene las publicaciones de las notificaciones por publicacion
         * @version 1.0
         */
       function obtenerPublicaciones(){

           vm.idsNotificacionPublicacion = [];
            for(var i=0;i<vm.notificacionesPublicacion.length;i++){
                var notificacion={
                    id : '',
                    estado: ''
                };
                vm.idPublicaciones[i]= vm.notificacionesPublicacion[i].link;
                notificacion.id = vm.notificacionesPublicacion[i].id;
                notificacion.estado = vm.notificacionesPublicacion[i].estado;
                vm.idsNotificacionPublicacion[i] = notificacion;
            }
            if( vm.idPublicaciones.length!=0){
                ListarNotificaciones.obtenerPublicaciones({ids: vm.idPublicaciones}).success(function(response) {
                    vm.listaPublicaciones=response;
                    if(vm.listaPublicaciones.length!= 0){
                        notificacionPublicacion();
                    }
                }).error(function (error){
                });
            }

       }

        /**
         * @author Maureen Leon
         * Construye la notificacion para mostrar de tipo publicacion
         * @version 1.0
         */
       function notificacionPublicacion () {
            obtenerUsuario();
           console.log( vm.listaNotificacionesPublicaciones);
       }
       function obtenerUsuario(){

           vm.escritores = [];
            for(var i=0;i<vm.listaPublicaciones.length;i++){
                vm.escritores[i] = vm.listaPublicaciones[i].usuarioId;
            }
            ListarNotificaciones.obtenerUsuariosNotificacion({ids : vm.escritores}).success(function(response) {
               vm.escritoresList = response;
                for(var i=0;i<vm.listaPublicaciones.length;i++){
                    vm.publi={
                        descripcion:vm.notificacionesPublicacion[i].descripcion,
                        descripcionPublicacion:vm.listaPublicaciones[i].descripcion,
                        url:vm.listaPublicaciones[i].fotopublicacionContentType,
                        titulo:vm.listaPublicaciones[i].titulo,
                        temaId:vm.listaPublicaciones[i].temaId,
                        categoriaId:vm.listaPublicaciones[i].categoriaId,
                        usuarioId:vm.escritoresList[i].idJHIUser,
                        id:vm.listaPublicaciones[i].id,
                        idNotiPubli : vm.idsNotificacionPublicacion[i].id,
                        estadoNotiPubli: vm.idsNotificacionPublicacion[i].estado

                }
                    vm.listaNotificacionesPublicaciones[i]= vm.publi;
                }

            }).error(function (error){
           });

       }


        /**
         * @author Maureen Leon
         * Obtiene  notificacion  de tipo seguidor
         * @version 1.0
         */
        vm.getSeguidores2=function(){
            vm.mostrar = 1;
            ListarNotificaciones.getSolicitudes({id: vm.usuario.id , opc: 1}).success(function(response) {
                vm.notificacionesSeguimiento=response;
                if( vm.notificacionesSeguimiento.length!= 0){
                    obtenerUsuarios();
                }
            }).error(function (error){
                 console.log(error);
                 if(error.description == "Internal server error"){
                     vm.errorCon =  true;
                 }
            });
        }

        /**
         * @author Maureen Leon
         * Obtiene  notificacion  de tipo publicacion
         * @version 1.0
         */
        vm.getPublicaciones2= function(){
            vm.mostrar = 2;
            ListarNotificaciones.getSolicitudes({id: vm.usuario.id , opc: 2}).success(function(response) {
                vm.notificacionesPublicacion=response;
                if( vm.notificacionesPublicacion.length!= 0){
                    obtenerPublicaciones();
                }
                cambiarNotiLikeEstado();
            }).error(function (error){
                if(error.description == "Internal server error"){
                    vm.errorCon =  true;
                }
            });

        }


        /**
         * @author Maureen Leon
         * Obtiene la lista de los usuarios que siguen a un usuario y han generado un notificacion
         * @version 1.0
         */
        function obtenerUsuarios(){

            vm.idsNotificacion=[];

            for(var i=0;i<vm.notificacionesSeguimiento.length;i++){
                var notificacion={
                    id : '',
                    estado: ''
                };
                vm.users[i]=vm.notificacionesSeguimiento[i].link;
                notificacion.id = vm.notificacionesSeguimiento[i].id;
                notificacion.estado = vm.notificacionesSeguimiento[i].estado;
                vm.idsNotificacion[i] = notificacion;

            }

            ListarNotificaciones.obtenerUsuariosNotificacion({ids: vm.users}).success(function(response) {
                vm.users=response;
                if(vm.users.length!= 0){
                    crearNotificacionesAmistad();
                }
            }).error(function (error){
            });
        }

        /**
         * @author Maureen Leon
         * Obtener notificaciones
         * @version 1.0
         */
        vm.getCompartidas =  function (){
            vm.mostrar = 3;
            ListarNotificaciones.getColaborativas({id:vm.usuario.id}).success(function(response) {
               vm.listaColaborativas = response;
               console.log(response);
                obtenerPublicacionNotificacion();
            }).error(function (error){
            });
        }

        /**
         * @author Maureen Leon
         * Funcion que se encarga de obtener el capitulo y luego enviarlo al editor de texto para que la persona colabora en la publicación,
         * tambien setea la notificación en true (osea no tiene que salir despues.)
         * @param idCapSiguiente
         * @version 1.0
         */
        vm.participar = function(idCapSiguiente) {
            var capituloSiguiente;
            ListarNotificaciones.obtenerCapitulo({id: parseInt(idCapSiguiente.link)}).success(function(result) {
                capituloSiguiente = result;
                ListarNotificaciones.cambiarEstadoNotificacion({idNotificacion:idCapSiguiente.id}).success(function(result){
                    console.log(result);
                    CompartidaServiceShare.enviarSiONo(capituloSiguiente);
                    $state.go('publicacionCompartidaEditor');
                })
            }).error(function(error){
            })
        }

        /**
         * @author Maureen Leon
         * Crea las notificacion para mostrar  con el tipo descripcion y el usuario que genero la publicacion
         * @version 1.0
         */
        function crearNotificacionesAmistad(){
            for(var i=0;i<vm.notificacionesSeguimiento.length;i++){
                vm.noti={descripcion: vm.notificacionesSeguimiento[i].descripcion,
                    tipo: vm.notificacionesSeguimiento[i].tipo,
                    idUsuario: vm.notificacionesSeguimiento[i].idUsuario,
                    link: vm.notificacionesSeguimiento[i].link,
                    idJHIUser:vm.users[i].idJHIUser,
                    url:vm.users[i].fotoperfilContentType,
                    nombre:vm.users[i].nombre+' '+vm.users[i].primerApelldio+' '+vm.users[i].segundoApellido,
                    idNotificacion : vm.idsNotificacion[i].id,
                    estadoNotificacion : vm.idsNotificacion[i].estado

                }
                vm.notificacionesAmigos[i]=vm.noti;
            }
        }
        vm.aumentarlimiteNotificaciones=function() {
            vm.limiteNotificaciones = vm.limiteNotificaciones + 2;
        }
        vm.aumentarlimiteNotificacionesPublicaciones=function() {
            vm.limiteNotificacionesPublicaciones = vm.limiteNotificacionesPublicaciones + 2;
        }

        /**
         * @author Jose Nuñez
         * Envía la publicación seleccionada al visor para poder verla.
         * Utiliza un broadcast para envíar la publicación
         */
        vm.verPublicacion = function (publicacion) {
            var url = $state.href('verPublicacion', {id: publicacion.id, tipo: 2});
            $window.open(url, '_blank');
        }

        /**
         * @author Christopher Sullivan
         * dirige a la pagina donde se somenta la publicacion
         * @param id
         * @version 1.0
         */
        vm.comentar=function (id,idNotiPubli) {

            ListarNotificaciones.cambiarEstadoNotificacion({idNotificacion: idNotiPubli}).success(function(response) {
                $state.go("comentarioSobrePublicacion",({idPublicacion:id}));
            }).error(function(error){
            })
        }

        function obtenerPublicacionNotificacion (){
            vm.capitulos= [];
            for(var i = 0 ;i<vm.listaColaborativas.length;i++){
                vm.capitulos[i]  =  vm.listaColaborativas[i].link;
            }

            ListarNotificaciones.obtenerPublicacionesPorIdCapitulo({capitulos : vm.capitulos}).success(function(response) {
              vm.publicacionesResponse = response;
               generarNotificacionesColaborativas();
            }).error(function(error){
            })
        }

       function generarNotificacionesColaborativas( ) {

           vm.notificacionesColaborativas = [];
           for(var i = 0 ;i<   vm.publicacionesResponse.length;i++){
               var notificacionCol ={
                   descripcion: vm.listaColaborativas[i].descripcion,
                   descripcionPublicacion : vm.publicacionesResponse[i].descripcion ,
                   imagenPublicacion: vm.publicacionesResponse[i].fotopublicacionContentType,
                   tituloPublicacion : vm.publicacionesResponse[i].titulo,
                   publicador : vm.publicacionesResponse[i].fotoPublicador,
                   link: vm.listaColaborativas[i].link,
                   estado: vm.listaColaborativas[i].estado,
                   id: vm.listaColaborativas[i].id
               }
               vm.notificacionesColaborativas.push(notificacionCol);
           }
       }

       vm.verSeguidor = function (jhiUserId,idNotificacion){
             console.log( jhiUserId +" "+idNotificacion );
             ListarNotificaciones.cambiarEstadoNotificacion({idNotificacion: idNotificacion}).success(function(response) {
                 $state.go('verPerfil', {id: jhiUserId});
             }).error(function(error){
             })
       }

       function cambiarNotiLikeEstado(){
           ListarNotificaciones.cambiarNotiLikeEstado({id:vm.usuario.id});

       }
    }
})();

