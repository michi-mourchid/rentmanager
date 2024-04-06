<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<html>
<%@include file="/WEB-INF/views/common/head.jsp"%>
<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">

    <%@ include file="/WEB-INF/views/common/header.jsp" %>
    <!-- Left side column. contains the logo and sidebar -->
    <%@ include file="/WEB-INF/views/common/sidebar.jsp" %>

    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper">
        <!-- Main content -->
        <section class="content">

            <div class="row">
                <div class="col-md-3">

                    <!-- Profile Image -->
                    <div class="box box-primary">
                        <div class="box-body box-profile">
                            <h3 class="profile-username text-center">Information de la reservation numero ${rent.id}</h3>

                            <ul class="list-group list-group-unbordered">
                                <li class="list-group-item">
                                    <b>Informations sur le client</b> <a class="pull-right" href="${pageContext.request.contextPath}/users/details?id=${client.id}">Numero ${client.id} - ${client.nom} ${client.prenom}</a>
                                </li>
                                <li class="list-group-item">
                                    <b>Informations sur le vehicule</b> <a class="pull-right" href="${pageContext.request.contextPath}/cars/details?id=${vehicle.id}">Numero ${vehicle.id} - ${vehicle.constructeur} ${vehicle.modele} a ${vehicle.nb_places} places</a>
                                </li>
                                <li class="list-group-item">
                                    <b>Date de debut de reservation</b> <a class="pull-right">${rent.debut}</a>
                                </li>
                                <li class="list-group-item">
                                    <b>Date de fin de reservation</b> <a class="pull-right">${rent.fin}</a>
                                </li>
                            </ul>
                        </div>
                        <!-- /.box-body -->
                    </div>
                    <!-- /.box -->
                </div>
                <!-- /.col -->
                <!-- /.col -->
            </div>
            <!-- /.row -->

        </section>
        <!-- /.content -->
    </div>

    <%@ include file="/WEB-INF/views/common/footer.jsp" %>
</div>
<!-- ./wrapper -->

<%@ include file="/WEB-INF/views/common/js_imports.jsp" %>
</body>
</html>
