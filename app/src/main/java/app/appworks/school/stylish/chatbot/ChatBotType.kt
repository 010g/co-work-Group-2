package app.appworks.school.stylish.chatbot

import android.content.res.Resources

data class HelloItem(
    val id :Long,
    val mainImage : String,
    val title : String
)

data class QuestionItem(
    val id :Long,
    val themeList : List<Theme>
)

data class AnswerItem(
    val id :Long,
    val answerContent :String
)


//-----///


data class Theme(
    val themeName : String,
    val nameList : List<String>
)

