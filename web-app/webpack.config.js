var path = require("path");

var DIST_DIR = path.resolve(__dirname, "dist");
var APP_DIR = path.resolve(__dirname, "app");

var config = {
    entry: APP_DIR + "/index.js",
    output: {
        path: DIST_DIR + "/app",
        filename: "bundle.js",
        publicPath: "/app/"
    }
};

module.exports = config;