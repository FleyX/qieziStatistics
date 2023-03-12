package controller

import "github.com/gin-gonic/gin"

var base = `/application`

func sign(c *gin.Context) {
	c.String(200, `ok`)
}

func visit(c *gin.Context) {

}

func check(c *gin.Context) {

}

func download(c *gin.Context) {

}

func LoadApplication(e *gin.Engine) {
	e.POST(base+"/sign", sign).
		GET(base+"/visit", visit).
		POST(base+"/check", check).
		GET(base+"/download", download)
}
