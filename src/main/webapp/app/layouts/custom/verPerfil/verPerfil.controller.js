(function () {
    'use strict';

    angular
        .module('digitalBlogApp')
        .controller('VerPerfilController',VerPerfilController);
    VerPerfilController.$inject=['$uibModal','$window','$scope','$stateParams','PersonalizarCuenta','VerPerfil','Principal','Seguidor','ListarSeguidos','ListarSeguidores','webSocketService','$state','PublicacionServiceShare','DejarSeguirServiceShare','LikeT','BibliotecaGeneralService','Publicacion'];
    function VerPerfilController($uibModal,$window,$scope,$stateParams,PersonalizarCuenta,VerPerfil,Principal,Seguidor,ListarSeguidos,ListarSeguidores,webSocketService,$state,PublicacionServiceShare,DejarSeguirServiceShare,LikeT,BibliotecaGeneralService,Publicacion) {
        var vm = this;
        vm.usuario ;
        vm.objSeguidor;
        vm.seguidores=[];
        vm.seguidos=[];
        vm.esSeguido=false;
        vm.objSeguimiento;
        vm.cantSeguidores;
        vm.cantSeguidos;
        vm.limite=3;
        vm.publicacionLikes=[];
        vm.errorConection=false;

        /**
         * Permite obtener la cuenta logueada actualmente
         */
        Principal.identity().then(function(account) {
            vm.cuentaActual = account;
        });

        /**
         * @author Maureen Leon
         * Recibe el id del usuario actual del cual se esta visualizando el perfil
         * @version 1.0
         */
        if($stateParams.id==''){
            vm.errorConection = true;

        }else{
            vm.idUsuarioActual= $stateParams.id;
            obtenerUsuarioNormal();
        }

        /**
         * @author Maureen Leon
         * Recibe el id del usuario logueado JHIUser y trae el Usuario de la tabla Usuarios
         * @version 1.0
         */
        function obtenerUsuarioNormal(){
            PersonalizarCuenta.obtenerUsuarioNormal(vm.idUsuarioActual).success(function(response){
                vm.usuario=response;
                cargarAllLikeT();
                obtenerPublicaciones();
                cargarContactos();
                verificarSeguidores();
            }).error(function (error){

                if(error.description == "Internal server error"){
                    vm.errorConection = true;
                }
                if(error.description == "Not Found"){
                    vm.errorConection = true;
                }
                console.log("Problema inesperado al traer el usuario con el id de JHIuser error:" + error);
            });
        }

        /**
         * @author Maureen Leon
         * Se obtienen las publicaciones del usuario actual
         * @version 1.0
         */
        function obtenerPublicaciones(){
            VerPerfil.obtenerPublicacionesPorUsuario(vm.usuario.id).success(function(response){
                vm.publicaciones = response;
                for (var i = 0; i < vm.publicaciones.length; i++) {
                    if (vm.publicaciones[i].contenido == null) {
                        vm.publicaciones[i].contenido = "0";
                        BibliotecaGeneralService.updatePublicacion(vm.publicaciones[i]);
                    }
                }
                vm.publicacionLikes = vm.publicaciones;
                cargarListaLikes();
            }).error(function (error){
                console.log("Problema inesperado al traer las pubicaciones de un usuario error: "+ error);
            });
        }

        vm.seguir = function(){

            if(vm.esSeguido==true){
                vm.noSeguir();

            }else{
                PersonalizarCuenta.obtenerUsuarioNormal(vm.cuentaActual.id).success(function(response){
                    vm.userSeguidor=response;
                    vm.objSeguidor={idSeguidoId: vm.usuario.id,
                        idSeguidorId:vm.userSeguidor.id};
                    vm.retorno=Seguidor.save(vm.objSeguidor);
                    vm.esSeguido=true;
                    vm.cantSeguidores=vm.cantSeguidores+1;
                    console.log( vm.retorno);
                    vm.icon="../../../../content/images/follow.svg";

                }).error(function (error){
                    console.log("Problema inesperado al traer el usuario con el id de JHIuser error: "+ error);
                });
            }
        }


        /**
         * obtiene el id del usuario logueado y el perfil actual para quitar el seguimiento
         */
        vm.noSeguir =function(){
            // obtiene el usuario de la tabla usuarios con el id del JHIuser
            PersonalizarCuenta.obtenerUsuarioNormal(vm.cuentaActual.id).success(function(response){
                vm.esSeguido=false;
                vm.objSeguimiento={idSeguidoId:vm.usuario.id,
                    idSeguidorId:response.id};
                vm.icon="../../../../content/images/unfollow.svg";

                obtenerSeguimientos();
            }).error(function (error){
                console.log("Problema inesperado al traer el usuario con el id de JHIuser error :" + error);
            });
        }
        /**
         * obtiene la lista de seguidores del usuario logueado , asi se puede validar cuales usuarios son seguidos o no
         */
        function obtenerSeguimientos(){
            VerPerfil.obtenerSeguimiento(vm.objSeguimiento).success(function(response){
                vm.objSeguimiento = response;
                vm.respuesta = Seguidor.delete(vm.objSeguimiento);
                var index = vm.seguidores.indexOf(vm.objSeguimiento);
                vm.seguidores.splice(index, 1);
                vm.cantSeguidores = vm.seguidores.length;
            }).error(function (error){
                console.log("Problema inesperado al traer seguimiento error: "+ error);
            });
        }


            /**
             * @author Maureen Leon
            * Obtiene los usuarios seguidores y seguidos del usuario actual
             * @version 1.0
            */
            function cargarContactos(){
            ListarSeguidores.obtenerSeguidores(vm.usuario.id).success(function(response){
                vm.seguidores=response;
                vm.cantSeguidores= vm.seguidores.length;

            }).error(function (error){
                console.log("Problema obtener seguidores error :"+ error);
            });
            ListarSeguidos.obtenerSeguidos(vm.usuario.id).success(function(response){
                vm.seguidos=response;
                vm.cantSeguidos= vm.seguidos.length;

            }).error(function (error){
                console.log("Problema obtener seguidos error:" + error);
            });
        }


        /**
         * @author Maureen Leon
        * verifica si los usuarios seguidos por el usuario logueado
         * @version 1.0
        */
        function verificarSeguidores(){
            vm.seguidosLoginUser=[];
            PersonalizarCuenta.obtenerUsuarioNormal(vm.cuentaActual.id).success(function(response){
                vm.loginUser=response;
                ListarSeguidos.obtenerSeguidos( vm.loginUser.id).success(function(response){
                    vm.seguidosLoginUser=response;
                    for(var i=0;i<vm.seguidosLoginUser.length;i++){
                        console.log(vm.seguidosLoginUser[i].id +'='+ vm.usuario.id);
                        if(vm.usuario.id === vm.seguidosLoginUser[i].id){
                            vm.esSeguido=true;
                        }
                    }
                    botonSeguir();
                }).error(function (error){
                    console.log("Problema obtener seguidos erroe:"+ error);
                });
            }).error(function (error){
                console.log("Problema inesperado al traer el usuario con el id de JHIuser error :" + error);
            });
        }

        /**
         * @author Christopher Sullivan
         * se encarga de amentar el arreglo o disminuirlo cada vez que sigue o dejan de seguir
         * @version 1.0
         */
        webSocketService.receive().then(null, null, function(activity) {
            if(activity.idUsuario==0){
                vm.cantSeguidores=vm.cantSeguidores-1;
            }else{
                vm.cantSeguidores=vm.cantSeguidores+1;
            }
        })

        /**
         * @author Maureen Leon
         * incrementa la cantidad de publicaciones q se van a mostrar
         * @version 1.0
         */
        vm.aumentarLimite = function(){
            vm.limite = vm.limite+2;
        }

        /**
         * @author Jose Nuñez
         * Envía la publicación seleccionada al visor para poder verla.
         * Utiliza un broadcast para envíar la publicación
         * @version 1.o
         */
        vm.verPublicacion = function(publicacion){
           // PublicacionServiceShare.enviarPublicacion(publicacion, 2);
            var url = $state.href('verPublicacion',{id: publicacion.id,tipo:2});
            $window.open(url,'_blank');
        }

        /**
         * @author Maureen Leon
         * Si el usuario sugue a otro  usuario muestra el boton apropiado
         * @version 1.0
         */
        function botonSeguir(){
            if( vm.esSeguido==true){
                vm.icon="../../../../content/images/follow.svg";
            }else{
                vm.icon="../../../../content/images/unfollow.svg";
            }
        }

        /**
         * @author Christopher Sullivan
         * dirige a la pagina donde se somenta la publicacion
         * @param id
         * @version 1.0
         */
        vm.comentar=function (id) {
            $state.go("comentarioSobrePublicacion",({idPublicacion:id}));
        }


        /**
         * Implementacion de likes en las publicaciones
         */

        /**
         * @author Eduardo Guerrero
         * Carga la lista de likes del usuario
         * Se crea un objeto publicacion añadiendole un atributo boolean al objeto publicacion
         * @version 1.0
         */
        function cargarListaLikes() {
            vm.listaPublicacionesLikes = [];
            for (var i = 0; i < vm.publicacionLikes.length; i++) {
                var publicacion = {
                    cantidadIteraciones: vm.publicacionLikes[i].cantidadIteraciones,
                    categoriaId: vm.publicacionLikes[i].categoriaId,
                    contenido: vm.publicacionLikes[i].contenido,
                    descripcion: vm.publicacionLikes[i].descripcion,
                    estado: vm.publicacionLikes[i].estado,
                    fotoPublicador: vm.publicacionLikes[i].fotoPublicador,
                    fotopublicacion: vm.publicacionLikes[i].fotopublicacion,
                    fotopublicacionContentType: vm.publicacionLikes[i].fotopublicacionContentType,
                    id: vm.publicacionLikes[i].id,
                    temaId: vm.publicacionLikes[i].temaId,
                    tipo: vm.publicacionLikes[i].tipo,
                    titulo: vm.publicacionLikes[i].titulo,
                    urlImagen: vm.publicacionLikes[i].urlImagen,
                    usuarioId: vm.publicacionLikes[i].usuarioId,
                    estadoLike: false
                };
                vm.listaPublicacionesLikes.push(publicacion);
            }
            verificarLikeSeguimiento();
        }

        /**
         * @author Eduardo Guerrero
         * Verficia el like de seguimiento con el usuario normal.
         * @version 1.0
         */
        function verificarLikeSeguimiento() {
            Principal.identity().then(function (account) {
                vm.cuentaActual = account;
                vm.LikesPublicacion = [];
                PersonalizarCuenta.obtenerUsuarioNormal(vm.cuentaActual.id).success(function (response) {
                    vm.UsuarioLogueado = response;
                    recorrerListaPublicacionesLikes();
                })
            })
        }

        /**
         * @author Eduardo Guerrero
         * Obtiene el dice de las publicaciones con likes
         * @version 1.0
         */
        function recorrerListaPublicacionesLikes(){
            for(var i = 0; i < vm.listaPublicacionesLikes.length;i++){
                verificarPublicacionLikeTS(i);
            }
        }

        /**
         * @author Eduardo Guerrero
         * Recorre la lista de likes del usuario
         * @param indice por cada iteracion de vm.listaPublicacionsLikes recibe el indice de la publicacion
         * y le asigna true a la publicacion que ya tiene like
         * @version 1.0
         */
        function verificarPublicacionLikeTS(indice){
            for(var i = 0; i < vm.likeTS.length; i++){
                if(vm.likeTS[i].idLikePId == vm.listaPublicacionesLikes[indice].id && vm.UsuarioLogueado.id == vm.likeTS[i].idUsuarioLId && vm.listaPublicacionesLikes[indice].contenido !== "0"){
                    vm.listaPublicacionesLikes[indice].estadoLike = true;
                }
            }
        }

        /**
         * @author Eduardo Guerrero
         * Carga todos los likes del usuario
         * @version 1.0
         */
        function cargarAllLikeT() {
            LikeT.query(function(result) {
                vm.likeTS = result;
                vm.searchQuery = null;
            });
        }

        /**
         * @author Eduardo Guerrero
         * Agrega el like a la publicacion
         * @param PublicacionObjeto recibe la publicacion como parametro
         * @param $index posicion de la publicacion a la cual le debe cambiar el icon.
         * @version 1.0
         */
        vm.like = function (PublicacionObjeto, $index) {
            PersonalizarCuenta.obtenerUsuarioNormal(vm.cuentaActual.id).success(function (data) {
                vm.usuarioNormal = data;
                vm.likeT = {
                    idUsuarioLId: vm.usuarioNormal.id,
                    idLikePId: PublicacionObjeto.id
                };
                //Busca el like del usuario por publicacion
                BibliotecaGeneralService.obtenerLikeDeUsuario({
                    idUsuario: vm.likeT.idUsuarioLId,
                    idPublicacion: vm.likeT.idLikePId
                }).success(function (data) {
                    vm.quitarLike(PublicacionObjeto, $index);
                }).error(function () {
                    BibliotecaGeneralService.likePublicacion({like: vm.likeT}).success(function () {
                        var contador = parseInt(PublicacionObjeto.contenido);
                        contador = contador + 1;
                        PublicacionObjeto.contenido = contador.toString();
                        BibliotecaGeneralService.updatePublicacion(PublicacionObjeto);
                        vm.listaPublicacionesLikes[$index].estadoLike = true;
                        cargarAllLikeT();
                    })
                })

            })
        }
        /**
         * @author Eduardo Guerrero
         * Quita el like a la publicacion
         * @param PublicacionObjeto recibe la publicacion como parametro
         * @param $index posicion de la publicacion a la cual le debe cambiar el icon.
         * @version 1.0
         */
        vm.quitarLike = function (PublicacionObjeto, $index) {
            BibliotecaGeneralService.obtenerLikeDeUsuario({
                idUsuario: vm.likeT.idUsuarioLId,
                idPublicacion: vm.likeT.idLikePId
            }).success(function (data) {
                vm.likeUsuario = data;
                LikeT.delete({id: vm.likeUsuario.id});
                var contador = parseInt(PublicacionObjeto.contenido);
                contador = contador - 1;
                PublicacionObjeto.contenido = contador.toString();
                vm.listaPublicacionesLikes[$index].estadoLike = false;
                BibliotecaGeneralService.updatePublicacion(PublicacionObjeto);
                cargarAllLikeT();
            })
        }


    }
})();
