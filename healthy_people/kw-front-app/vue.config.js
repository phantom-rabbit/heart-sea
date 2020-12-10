'use strict'
const path = require('path')

function resolve(dir) {
    return path.join(__dirname, dir)
}

const name = 'OPC生态通证'
// const AliyunossWebpackPlugin = require('webpack-aliyun-oss-plugin');
module.exports = {
    publicPath: './',
    // 输出文件目录,默认就是dist
    outputDir: 'dist',
    // 放置生成的静态资源 (js、css、img、fonts) 的 (相对于 outputDir 的) 目录。
    assetsDir: 'static',
    // 生产环境是否生成 sourceMap 文件
    productionSourceMap: false,
    pages: {
        index: {
            // page 的入口
            entry: 'src/main.js',
            // 模板来源
            template: 'public/index.html',
            // 在 dist/index.html 的输出
            filename: 'index.html',
            // 当使用 title 选项时，
            // template 中的 title 标签需要是 <title><%= htmlWebpackPlugin.options.title %></title>
            title: name,
        }
    },
    // devServer: {
    //     port: 8081,
    //     open: false,
    //     overlay: {
    //         warnings: false,
    //         errors: true
    //     },
    //     proxy: {
    //         '/api': {
    //             target: process.env.VUE_APP_BASE_API,
    //             changeOrigin: true,
    //             pathRewrite: {
    //                 '/api': ''
    //             }
    //         }
    //     },
    // },
    css: {  // 一次配置，全局使用，这个scss 因为每个文件都要引入
        loaderOptions: {
            sass: {
                prependData: `@import "./src/assets/css/scss-base.scss";`
            },
            postcss: {
                plugins: [
                    require("postcss-px-to-viewport")({
                        unitToConvert: "px",
                        viewportWidth: 750,
                        viewportHeight: 1230,
                        unitPrecision: 3,
                        propList: [
                            "*"
                        ],
                        viewportUnit: "vw",
                        fontViewportUnit: "vw",
                        selectorBlackList: ['.ignore','.hairlines',"el-","van-","vue-"],
                        minPixelValue: 1,
                        mediaQuery: false,
                        replace: true,
                        exclude: /(\/|\\)(node_modules)(\/|\\)/,
                    })
                ]
            }

        }
    },
    configureWebpack: {
        plugins: [

        ],
        name: name,
        resolve: {
            alias: {
                '@': resolve('src')
            }
        }
    }
}
