@file:JsModule("rc-progress")

package ui.common

import react.RClass
import react.RProps

@JsName("Line")
external val Line: RClass<ProgressBarProps>

@JsName("Circle")
external val Circle: RClass<ProgressBarProps>

external interface ProgressBarProps : RProps {
    var prefixCls: String?
    var className: String?
    var type: String?
    var percent: Number?
    var successPercent: Number?
    var format: ((percent: Number?, successPercent: Number?) -> Any /* String | ReactElement */)?
    var status: String?
    var showInfo: Boolean?
    var strokeWidth: Number?
    var strokeLinecap: String?
    var strokeColor: String? /* String | ProgressGradient */
    var trailColor: String?
    var width: Number?
    var style: dynamic
    var gapDegree: Number?
    var gapPosition: String?
    var size: String?
}


