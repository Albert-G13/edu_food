<#import "spring.ftl" as spring>
<#macro layout>
    <!DOCTYPE html>
    <html lang="ru">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport"
              content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
        <meta http-equiv="X-UA-Compatible" content="ie=edge">
        <meta name="_csrf_header" content="${_csrf.headerName}">
        <meta name="_csrf_token" content="${_csrf.token}">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css"
              rel="stylesheet" integrity="sha384-sRIl4kxILFvY47J16cr9ZwB07vP4J8+LH7qKQnuqkuIAvNWLzeN8tE5YBujZqJLB"
              crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js"
                integrity="sha384-FKyoEForCGlyvwx9Hj09JcYn3nv7wiPVlz7YYwJrWVcXK/BmnVDxM+D2scQbITxI"
                crossorigin="anonymous"></script>
        <title>Job search</title>
    </head>
    <body>

    <nav class="navbar navbar-expand-lg navbar-dark bg-dark shadow-sm">
        <div class="container">
            <a class="navbar-brand fw-bold" href="/restaurants">EduFood</a>

            <div class="d-flex align-items-center">
                <a href="/basket" class="btn btn-outline-light me-3">
                    Корзина 🛒 <span class="badge bg-danger">${cartCount!0}</span>
                </a>

                <#assign known = SPRING_SECURITY_CONTEXT??>
                <#if known>
                    <a class="btn btn-outline-light me-2" href="/users/profile">Мой профиль</a>
                    <form action="/auth/logout" method="post" class="m-0">
                        <#if _csrf??>
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        </#if>
                        <button type="submit" class="btn btn-sm btn-danger">Выход</button>
                    </form>
                <#else>
                    <a href="/auth/login" class="btn btn-sm btn-success">Вход</a>
                </#if>

            </div>
        </div>
    </nav>

    <div class="container main-container mb-3 mt-3 d-flex flex-column min-vh-100">
        <#nested spring>
    </div>
    <footer class="footer py-3 mt-auto bg-dark">
        <div class="container">
            <p class="text-light">Edu Food</p>
        </div>
    </footer>
    </body>
    </html>
</#macro>