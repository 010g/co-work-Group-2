package app.appworks.school.stylish

object FBLoginResult {
    var fbFirstName : String = ""
    var fbLastName : String = ""
    var fbEmail : String = ""
}

object NativeLoginResult{
    var nativeId : Int? = -1
        set(value) {
            field = value
        }
    var nativeName : String = ""
    var nativeEmail : String = ""
    var nativePicture : String? = ""
    var nativeProvider : String = ""
}