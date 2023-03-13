package util

import "github.com/gin-gonic/gin"

type result struct {
	Code    int
	Message string
	Data    any
}

func Success(ctx *gin.Context, data any) {
	ctx.JSON(200, result{200, "success", data})
}

func Fail(ctx *gin.Context) {
	ctx.JSON(200, result{0, "fail", nil})
}
