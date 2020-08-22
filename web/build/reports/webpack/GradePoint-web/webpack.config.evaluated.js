{
  mode: 'development',
  resolve: {
    modules: [
      'S:\\AndroidStudioProjects\\GradePoint\\build\\js\\packages\\GradePoint-web\\kotlin-dce-dev',
      'node_modules'
    ]
  },
  plugins: [
    TeamCityErrorPlugin {}
  ],
  module: {
    rules: [
      {
        test: /\.js$/,
        use: [
          'source-map-loader'
        ],
        enforce: 'pre'
      }
    ]
  },
  entry: {
    main: [
      'S:\\AndroidStudioProjects\\GradePoint\\build\\js\\packages\\GradePoint-web\\kotlin-dce-dev\\GradePoint-web.js'
    ]
  },
  output: {
    path: 'S:\\AndroidStudioProjects\\GradePoint\\web\\build\\distributions',
    filename: [Function: filename],
    library: 'web',
    libraryTarget: 'umd'
  },
  devtool: 'eval-source-map',
  stats: {
    warningsFilter: [
      /Failed to parse source map/
    ],
    warnings: false,
    errors: false
  },
  devServer: {
    inline: true,
    lazy: false,
    noInfo: true,
    open: true,
    overlay: false,
    contentBase: [
      'S:\\AndroidStudioProjects\\GradePoint\\web\\build\\processedResources\\js\\main'
    ]
  }
}