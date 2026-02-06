<#import "../layout.ftlh" as main>
<@main.layout ; spring>
    <div class="row justify-content-center">
        <div class="col-md-5 card shadow p-4 mt-5">
            <h3 class="text-center mb-4">Регистрация</h3>

            <form action="/auth/register" method="post">
                <#if _csrf??>
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                </#if>

                <div class="mb-3">
                    <label class="form-label">Ваше имя</label>
                    <@spring.formInput "user.name" 'class="form-control" placeholder="Иван Иванович"' />
                    <@spring.showErrors "<br>" "text-danger small"/>
                </div>

                <div class="mb-3">
                    <label class="form-label">Email</label>
                    <@spring.formInput "user.email" 'class="form-control" placeholder="example@mail.com"' "email" />
                    <@spring.showErrors "<br>" "text-danger small"/>
                </div>

                <div class="mb-3">
                    <label class="form-label">Пароль</label>
                    <@spring.formInput "user.password" 'class="form-control"' "password" />
                    <@spring.showErrors "<br>" "text-danger small"/>
                </div>

                <button type="submit" class="btn btn-success w-100">Создать аккаунт</button>
            </form>

            <div class="mt-3 text-center">
                <small>Уже есть профиль? <a href="/auth/login">Войти</a></small>
            </div>
        </div>
    </div>
</@main.layout>