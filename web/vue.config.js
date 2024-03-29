module.exports = {
    devServer: {
        open: true,
        host: '0.0.0.0',
        port: 9090,
        https: false,
        //以上的ip和端口是我们本机的;下面为需要跨域的
        proxy: { //配置跨域
            '/sys': {
                target: 'http://localhost:8661/', //这里后台的地址模拟的;应该填写你们真实的后台接口
                ws: true,
                changOrigin: true, //允许跨域
                pathRewrite: {
                    '^/sys': '' //请求的时候使用这个api就可以
                }
            },
            '/test': {
                target: 'http://localhost:8661/', //这里后台的地址模拟的;应该填写你们真实的后台接口
                ws: true,
                changOrigin: true, //允许跨域
                pathRewrite: {
                    '^/test': '' //请求的时候使用这个api就可以
                }
            },
            '/web': {
                target: 'http://localhost:8661/', //这里后台的地址模拟的;应该填写你们真实的后台接口
                ws: true,
                changOrigin: true, //允许跨域
                pathRewrite: {
                    '^/web': '' //请求的时候使用这个api就可以
                }
            }
        }
    },
    configureWebpack: {

        performance: {
            hints: 'warning',
            //入口起点的最大体积
            maxEntrypointSize: 50000000,
            //生成文件的最大体积
            maxAssetSize: 30000000,
            //只给出 js 文件的性能提示
            assetFilter: function(assetFilename) {
                return assetFilename.endsWith('.js');
            }
        }
    },
    publicPath: './',



}
