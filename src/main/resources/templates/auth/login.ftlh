<#import "../layout.ftlh" as main>
<@main.layout>
    <div class="row justify-content-center">
        <div class="col-md-5 card shadow p-4 mt-5">
            <h3 class="text-center mb-4">Вход</h3>

            <#if RequestParameters.error??>
                <div class="alert alert-danger" role="alert">
                    Неверный email или пароль
                </div>
            </#if>

            <#if RequestParameters.logout??>
                <div class="alert alert-success" role="alert">
                    Вы успешно вышли из системы
                </div>
            </#if>

            <form action="/auth/login" method="post">
                <#if _csrf??>
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                </#if>
                <div class="mb-3">
                    <label class="form-label">Email</label>
                    <input type="email" name="username" class="form-control"
                           placeholder="example@mail.com" required autofocus>
                </div>

                <div class="mb-3">
                    <label class="form-label">Пароль</label>
                    <input type="password" name="password" class="form-control" required>
                </div>

                <button type="submit" class="btn btn-primary w-100 py-2">Войти</button>
            </form>

            <hr class="my-4">

            <div class="text-center">
                <p class="mb-0 text-muted">Впервые у нас?</p>
                <a href="/auth/register" class="btn btn-link text-decoration-none">Создать аккаунт</a>
            </div>
        </div>
    </div>
</@main.layout>