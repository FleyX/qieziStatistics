package util

import (
	"github.com/spf13/viper"
	"os"
)

type Mysql struct {
	User     string
	Password string
	Host     string
	Port     int
	Db       string
}

type Redis struct {
	Host string
	Port int
	Db   int
}

type AppConfig struct {
	Mysql Mysql
	Redis Redis
}

var Config *AppConfig

func InitConfig() {
	Config = &AppConfig{}
	v := viper.New()
	v.SetConfigName("config")
	wd, _ := os.Getwd()
	v.AddConfigPath(wd)
	v.SetConfigType("yml")

	if err := v.ReadInConfig(); err != nil {
		panic(err)
	}
	envConfig := os.Getenv("CONFIG_FILE_NAME")
	if len(envConfig) > 0 {
		v.SetConfigName(envConfig)
		if err := v.ReadInConfig(); err != nil {
			panic(err)
		}
	}
	if err := v.Unmarshal(Config); err != nil {
		panic(err)
	}
}
