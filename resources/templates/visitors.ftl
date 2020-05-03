<#import "common/bootstrap.ftl" as b>

<@b.page>
    <#if visitors?? && (visitors?size > 0)>
            <table class="table table-striped">
                <thead>
                    </tr>
                        <th>Staff Name</th>
                        <th>Faculty</th>
                        <th>Department</th>
                        <th>Reason of Visit</th>
                    </tr>
                </thead>
                <tbody>
                    <#list visitors as visitor>
                        <tr>
                            <td style="vertical-align:middle"><h5>${visitor.staffName}</h5></td>
                            <td style="vertical-align:middle"><h5>${visitor.faculty}</h5></td>
                            <td style="vertical-align:middle"><h5>${visitor.dept}</h5></td>
                            <td style="vertical-align:middle"><h5>${visitor.visitReason}</h5></td>
                            <td class="col-md-1" style="text-align:center; vertical-align:middle;">

                                <form method="post" action="/visitors">
                                    <input type="hidden" name="date" value="${date?c}">
                                    <input type="hidden" name="code" value="${code}">
                                    <input type="hidden" name="id" value="${visitor.id}">
                                    <input type="hidden" name="action" value="edit">
                                    <input type="image" src="/static/edit.png" width="18" height="18" border="0" alt="Edit"/>
                                </form>

                                <form method="post" action="/visitors">
                                    <input type="hidden" name="date" value="${date?c}">
                                    <input type="hidden" name="code" value="${code}">
                                    <input type="hidden" name="id" value="${visitor.id}">
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