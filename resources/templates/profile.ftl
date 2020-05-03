<#import "common/bootstrap.ftl" as b>
<@b.page>
  <form method="post" action="/profile" enctype="multipart/form-data" class="form-horizontal container">
    <input type="hidden" name="action" value="upload">
    <div class="form-group">
        <label for="staffName" class="col-sm-2 control-label">Staff Name</label>
        <div class="col-sm-4">
            <input type="file" name="uploadedFile" class="form-control" size="25" id="uploadedFile" />
        </div>
    </div>
    <div class="form-group">
        <div class="col-sm-offset-2 col-sm-4">
            <button type="submit" class="btn btn-primary btn-lg btn-block">Upload</button>
        </div>
    </div>
  </form>
  <#if profileUrls??>
  <img src="/static/${profileUrls}" class="img-thumbnail" width="300" height="300" alt="profile image"/>
  </#if>

</@b.page>
