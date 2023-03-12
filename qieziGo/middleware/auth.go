package middleware

import (
	"github.com/gin-gonic/gin"
	"net/http"
	"time"
)

var ok = false

func Auth() gin.HandlerFunc {
	return func(context *gin.Context) {
		t := time.Now()
		println("请求时间：%s", t.String())
		if ok {
			context.Next()
		} else {
			context.Status(http.StatusUnauthorized)
			context.Writer.WriteString(`无访问权限`)
			context.Abort()
		}
		ok = !ok
		println("请求结束时间：%s", time.Now().String())

	}
}
