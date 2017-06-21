<!DOCTYPE html>
<html lang="en">
<%@include file="/WEB-INF/jsp/common/head.jsp" %>
<link rel="stylesheet" type="text/css"
      href="${static_resource}/static/theme/ninestars/bootstrap/css/bootstrap.min.css"/>
<link rel="stylesheet" type="text/css"
      href="${static_resource}/static/theme/ninestars/font-awesome/css/font-awesome.min.css"/>
<link href="${static_resource}/static/theme/ninestars/css/nivo-lightbox.css" rel="stylesheet"/>
<link href="${static_resource}/static/theme/ninestars/css/nivo-lightbox-theme/default/default.css" rel="stylesheet"/>
<link href="${static_resource}/static/theme/ninestars/css/animate.css" rel="stylesheet"/>
<link href="${static_resource}/static/theme/ninestars/css/style.css" rel="stylesheet">
<link href="${static_resource}/static/theme/ninestars/css/default.css" rel="stylesheet">
<link href="${static_resource}/webjars/datatables/1.10.13/media/css/dataTables.bootstrap.css" rel="stylesheet">
<%--</head>--%>
<body data-spy="scroll">

<div class="container">
    <ul id="gn-menu" class="gn-menu-main">
        <li class="gn-trigger">
            <a class="gn-icon gn-icon-menu"><span>Menu</span></a>
            <nav class="gn-menu-wrapper">
                <div class="gn-scroller">
                    <ul class="gn-menu">
                        <li class="gn-search-item">
                            <input placeholder="Search" type="search" class="gn-search">
                            <a class="gn-icon gn-icon-search"><span>Search</span></a>
                        </li>
                        <li>
                            <a href="#about" class="gn-icon gn-icon-download">load page</a>
                        </li>
                        <li><a href="#service" class="gn-icon gn-icon-cog">datatable</a></li>
                        <li><a href="#contact" class="gn-icon gn-icon-help">edit</a></li>
                    </ul>
                </div>
                <!-- /gn-scroller -->
            </nav>
        </li>
        <li><a href="index.html">NINESTARS</a></li>
        <li>
            <ul class="company-social">
                <li class="social-facebook"><a href="#" target="_blank"><i class="fa fa-facebook"></i></a></li>
                <li class="social-twitter"><a href="#" target="_blank"><i class="fa fa-twitter"></i></a></li>
                <li class="social-dribble"><a href="#" target="_blank"><i class="fa fa-dribbble"></i></a></li>
                <li class="social-google"><a href="#" target="_blank"><i class="fa fa-google-plus"></i></a></li>
            </ul>
        </li>
    </ul>
</div>

<!-- Section: intro -->
<section id="intro" class="intro" style="background-size: cover;">
    <div class="slogan">
        <h1>This is Ninestars</h1>
        <p>make awesome stuff with Bootstrap framework</p>
        <a href="#about" class="btn btn-skin scroll">Learn more</a>
    </div>
</section>
<!-- /Section: intro -->

<!-- Section: about -->
<section id="about" class="home-section text-center bg-gray">
    <div class="heading-about marginbot-50">
        <div class="container">
            <div class="row">
                <div class="col-lg-8 col-lg-offset-2">
                    <div class="section-heading">
                        <h2 style="cursor: pointer;" onclick="load_url('helloJsp.html', $('#lp'));">load page</h2>
                        <p>
                            onclick="load_url('helloJsp.html', $('#lp'));
                        </p>
                    </div>

                </div>
            </div>
        </div>
    </div>
    <div class="container" id="lp">
        <div class="row">
            <div class="col-xs-6 col-sm-3 col-md-3">
                <div class="team boxed-grey">
                    <div class="inner">
                        <h5>Anna Hanaceck</h5>
                        <p class="subtitle">Pixel Crafter</p>
                        <div class="avatar">
                            <img src="${static_resource}/static/theme/ninestars/images/team/1.jpg" alt=""
                                 class="img-responsive"/></div>
                    </div>
                </div>
            </div>
            <div class="col-xs-6 col-sm-3 col-md-3">
                <div class="team boxed-grey">
                    <div class="inner">
                        <h5>Maura Daniels</h5>
                        <p class="subtitle">Ruby on Rails</p>
                        <div class="avatar">
                            <img src="${static_resource}/static/theme/ninestars/images/team/2.jpg" alt=""
                                 class="img-responsive"/></div>
                    </div>
                </div>
            </div>
            <div class="col-xs-6 col-sm-3 col-md-3">
                <div class="team boxed-grey">
                    <div class="inner">
                        <h5>Jack Briane</h5>
                        <p class="subtitle">jQuery Ninja</p>
                        <div class="avatar">
                            <img src="${static_resource}/static/theme/ninestars/images/team/3.jpg" alt=""
                                 class="img-responsive"/></div>
                    </div>
                </div>
            </div>
            <div class="col-xs-6 col-sm-3 col-md-3">
                <div class="team boxed-grey">
                    <div class="inner">
                        <h5>Tom Petterson</h5>
                        <p class="subtitle">Typographer</p>
                        <div class="avatar">
                            <img src="${static_resource}/static/theme/ninestars/images/team/4.jpg" alt=""
                                 class="img-responsive"/></div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>
<!-- /Section: about -->


<!-- Section: services -->
<section id="service" class="home-section text-center">
    <div class="heading-about marginbot-50">
        <div class="container">
            <div class="row">
                <div class="col-lg-8 col-lg-offset-2">

                    <div class="section-heading">
                        <h2 style="cursor: pointer;" onclick="dataTables();">datatable</h2>
                        <div style="text-align: left;padding-left: 100px;">
                            var columns = [ <br/>
                            {"sClass": "text-left", "data": "guid"}, <br/>
                            {"sClass": "text-left", "data": "account"}, <br/>
                            {"sClass": "text-left", "data": "password"}, <br/>
                            {<br/>
                            "sClass": "text-left", "data": function (obj) {<br/>
                            return format(obj.createtime, 'yyyy-MM-dd');<br/>
                            }<br/>
                            }];<br/>
                            var dataTables = function () {<br/>
                            return matchTable('getData.ajax', '#datatable', columns, {<br/>
                            'account': 'sdssdsdd',<br/>
                            'initpassword': 'sdssdsdd',<br/>
                            'password': 'sdssdsdd'<br/>
                            });<br/>
                        </div>
                    </div>

                </div>
            </div>
        </div>
    </div>
    <table id="datatable" class="container display table-striped table-bordered table-hover table-condensed"
           cellspacing="0" width="100%">
        <thead>
        <tr>
            <th>guid</th>
            <th>account</th>
            <th>password</th>
            <th>createtime</th>
        </tr>
        </thead>
    </table>
</section>
<!-- /Section: services -->
<script>
    var columns = [
        {"sClass": "text-left", "data": "guid"},
        {"sClass": "text-left", "data": "account"},
        {"sClass": "text-left", "data": "password"},
        {
            "sClass": "text-left", "data": function (obj) {
            return format(obj.createtime, 'yyyy-MM-dd');
        }
        }
    ];
    var dataTables = function () {
        return matchTable('getData.ajax', '#datatable', columns, {
            'account': 'sdssdsdd',
            'initpassword': 'sdssdsdd',
            'password': 'sdssdsdd'
        });
    }
</script>

<!-- Section: contact -->
<section id="contact" class="home-section text-center bg-gray">
    <div class="heading-contact marginbot-50">
        <div class="container">
            <div class="row">
                <div class="col-lg-8 col-lg-offset-2">

                    <div class="section-heading">
                        <h2>Get in touch</h2>
                        <p>Lorem ipsum dolor sit amet, no nisl mentitum recusabo per, vim at blandit qualisque
                            dissentiunt. Diam efficiantur conclusionemque ut has</p>
                    </div>

                </div>
            </div>
        </div>
    </div>
    <div class="container">
        <div class="row">
            <div class="col-lg-8 col-md-offset-2">
                <div class="boxed-grey">
                    <form id="contact-form">
                        <div class="row">
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label for="account">account</label>
                                    <input type="text" class="form-control" id="account" name="account" placeholder="Enter account"/>
                                </div>
                                <div class="form-group">
                                    <label for="password">password</label>
                                    <input type="text" class="form-control" id="password" name="password" placeholder="Enter password"/>
                                </div>
                                <div class="form-group">
                                    <label for="email">initpassword</label>
                                    <input type="email" class="form-control" id="initpassword" name="initpassword"
                                           placeholder="Enter initpassword"/>
                                </div>
                                <div class="form-group">
                                    <label for="email">Email Address</label>
                                    <div class="input-group">
                                        <span class="input-group-addon"><span
                                                class="glyphicon glyphicon-envelope"></span></span>
                                        <input type="email" class="form-control" id="email" placeholder="Enter email"/>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label for="subject">Subject</label>
                                    <select id="subject" name="subject" class="form-control">
                                        <option value="na" selected="">Choose One:</option>
                                        <option value="service">General Customer Service</option>
                                        <option value="suggestions">Suggestions</option>
                                        <option value="product">Product Support</option>
                                    </select>
                                </div>
                                <div class="form-group">
                                    <label for="message">Message</label>
                                    <textarea name="message" id="message" class="form-control" rows="9" cols="25"
                                              placeholder="Message"></textarea>
                                </div>
                            </div>
                            <div class="col-md-12">
                                <button type="button" class="btn btn-skin pull-right" id="btnContactUs"
                                        onclick="editSubmit()">
                                    Send Message
                                </button>
                            </div>
                        </div>
                    </form>
                </div>

                <div class="widget-contact row">
                    <div class="col-lg-6">
                        <address>
                            <strong>Ninestars Ltd.</strong><br>
                            Big Villa 334 Awesome, Beautiful Suite 1200<br>
                            San Francisco, CA 94107<br>
                            <abbr title="Phone">P:</abbr>
                            (123) 456-7890
                        </address>
                    </div>
                    <div class="col-lg-6">
                        <address>
                            <strong>Email</strong><br>
                            <a href="mailto:#">email.name@example.com</a><br/>
                            <a href="mailto:#">name.name@example.com</a>
                        </address>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>
<script>
    var editSubmit = function () {
        var formParams = $("#contact-form").serializeJson();
//        var opt = {
//            handler: function (result) {
//                if (!result.success) {
//                    alert(result.messages);
//                } else {
//                    alert(result.messages, function () {
//                        alert('页面跳转');
//                    });
//                }
//            }
//        };
        ajax_action('saveData.ajax', formParams);
    }
</script>

<%@include file="/WEB-INF/jsp/common/script.jsp" %>
<script type="text/javascript"
        src="${static_resource}/webjars/datatables/1.10.13/media/js/jquery.dataTables.js"></script>
<!-- /Section: contact -->

<!-- Core JavaScript Files -->
<script src="${static_resource}/static/theme/ninestars/js/jquery.easing.min.js"></script>
<script src="${static_resource}/static/theme/ninestars/js/classie.js"></script>
<script src="${static_resource}/static/theme/ninestars/js/gnmenu.js"></script>
<script src="${static_resource}/static/theme/ninestars/js/jquery.scrollTo.js"></script>
<script src="${static_resource}/static/theme/ninestars/js/nivo-lightbox.min.js"></script>
<script src="${static_resource}/static/theme/ninestars/js/stellar.js"></script>

<!-- Custom Theme JavaScript -->
<script src="${static_resource}/static/theme/ninestars/js/custom.js"></script>
<script>
    $(function () {

    });
</script>
</body>
</html>
