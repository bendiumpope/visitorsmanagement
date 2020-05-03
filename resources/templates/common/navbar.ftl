<nav class="navbar navbar-default">
    <div class="container-fluid">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="#bs-example-navbar-collapse-1">
             <span class="sr-only">Toggle navigation</span>
             <span class="icon-bar"></span>
             <span class="icon-bar"></span>
             <span class="icon-bar"></span>
             </button>
             <a class="navbar-brand" href="/">EmojiPhrases</a>
        </div>
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav">
                <li><a href="/">Home</a></li>
                <li><a href="/about">About</a></li>
                 <li><a href="/createvisitors">Visit Request</a></li>
                <li><a href="/visitors">Visitors</a></li>
                <li><a href="/users">Users</a></li>
                <li><a href="/profile">Profile</a></li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <#if profileUrls??>
                  <span width="5" />
                    <img src="/static/${profileUrls}" class="img-thumbnail" width="60" height="60" alt="profile image"/>
                </#if>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <#if user??>
                    <li><p class="navbar-text">Welcome ${user.userId}</p></li>
                    <li><a href="/signout">Sign Out</a></li>
                    <#else>
                    <li><a href="/signin">Sign In</a></li>
                    <li><a href="/signup">Sign Up</a></li>
                </#if>
            </ul>
        </div>
    </div>
</nav>