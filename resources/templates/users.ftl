<#import "common/bootstrap.ftl" as b>

<@b.page>
    <#if users?? && (users?size > 0)>
            <table class="table table-striped">
                <thead>
                    </tr>
                        <th>UserId</th>
                        <th>First Name</th>
                        <th>Last Name</th>
                        <th>Email</th>
                        <th>Org Name</th>
                        <th>Org Address</th>
                        <th>password Hash</th>
                    </tr>
                </thead>
                <tbody>
                    <#list users as user>
                        <tr>
                            <td style="vertical-align:middle"><h5>${user.userId}</h5></td>
                            <td style="vertical-align:middle"><h5>${user.firstName}</h5></td>
                            <td style="vertical-align:middle"><h5>${user.lastName}</h5></td>
                            <td style="vertical-align:middle"><h5>${user.emailAddress}</h5></td>
                            <td style="vertical-align:middle"><h5>${user.orgName}</h5></td>
                            <td style="vertical-align:middle"><h5>${user.orgAddress}</h5></td>
                            <td style="vertical-align:middle"><h5>${user.passwordHash}</h5></td>
                            <td class="col-md-1" style="text-align:center; vertical-align:middle;">

                                <form method="post" action="/users">
                                    <input type="hidden" name="date" value="${date?c}">
                                    <input type="hidden" name="code" value="${code}">
                                    <input type="hidden" name="id" value="${user.userId}">
                                    <input type="hidden" name="action" value="edit">
                                    <input type="image" src="/static/edit.png" width="18" height="18" border="0" alt="Edit"/>
                                </form>

                                <form method="post" action="/users">
                                    <input type="hidden" name="date" value="${date?c}">
                                    <input type="hidden" name="code" value="${code}">
                                    <input type="hidden" name="id" value="${user.userId}">
                                    <input type="hidden" name="action" value="delete">
                                    <input type="image" src="/static/delete.png" width="18" height="18" border="0" alt="Delete"/>
                                </form>
                            </td>
                        </tr>
                    </#list>
                </tbody>
            </table>
        </#if>
</@b.page>