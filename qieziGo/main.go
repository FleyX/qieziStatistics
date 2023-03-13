package main

import (
	"fmt"
	"qieziGo/controller"
	"qieziGo/middleware"

	"github.com/gin-gonic/gin"
)

func main() {
	r := gin.Default()
	r.Use(middleware.Auth())
	v1 := r.Group("/qiezi/api")
	controller.LoadApplication(v1)
	if err := r.Run(":8080"); err != nil {
		fmt.Printf("start service error:%v\n", err)
	}
}
