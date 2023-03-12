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
	controller.LoadApplication(r)
	if err := r.Run(":8080"); err != nil {
		fmt.Printf("start service error:%v\n", err)
	}
}
