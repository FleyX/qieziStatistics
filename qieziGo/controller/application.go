package controller

import (
	"encoding/json"
	"github.com/gin-gonic/gin"
	"qieziGo/util"
)

var base = `/application`

type test struct {
	aa string
	bb string
}

func sign(c *gin.Context) {
	data, _ := json.Marshal(test{"asdf", "asdf"})
	util.Success(c, data)
}

func visit(c *gin.Context) {

}

func check(c *gin.Context) {

}

func download(c *gin.Context) {

}

func LoadApplication(e *gin.RouterGroup) {
	e.POST(base+"/sign", sign).
		GET(base+"/visit", visit).
		POST(base+"/check", check).
		GET(base+"/download", download)
}
