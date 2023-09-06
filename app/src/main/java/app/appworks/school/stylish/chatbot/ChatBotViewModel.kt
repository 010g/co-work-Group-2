package app.appworks.school.stylish.chatbot

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import app.appworks.school.stylish.data.source.StylishRepository

class ChatBotViewModel(private val stylishRepository: StylishRepository) : ViewModel() {

    private val _chatBotContentListForAdapter = MutableLiveData<List<ChatBotDataItem>?>()

    val chatBotContentListForAdapter: LiveData<List<ChatBotDataItem>?>
        get() = _chatBotContentListForAdapter

    private val dataList = mutableListOf<ChatBotDataItem>()


    private val helloItem = ChatBotDataItem.ForHelloItem(HelloItem(id = 1,
        mainImage = "https://school.appworks.tw/wp-content/uploads/2023/05/cropped-Kash.jpg",
        title = "您的專屬客服 Kash 葛格上線啦~")
    )

    private val questionItem = ChatBotDataItem.ForQuestionItem(QuestionItem(id=2, themeList = listOf(Theme(themeName = "主題1", nameList = listOf("按鈕1","按鈕2")),Theme(themeName = "主題2", nameList = listOf("按鈕3","按鈕4","按鈕5"))))
    )

    private val deliveryInfoItem = ChatBotDataItem.ForAnswerItem(AnswerItem(id=3, answerContent = "配送問題請直接撥打專線，將會由專人為您服務"))

    private val processInfoItem = ChatBotDataItem.ForAnswerItem(AnswerItem(id=4, answerContent = ">O< 我查一下！"))

    private val exchangeInfoItem = ChatBotDataItem.ForAnswerItem(AnswerItem(id=5, answerContent = "真...的要退...嗎 (無辜委屈"))

    private val orderInfoItem = ChatBotDataItem.ForAnswerItem(AnswerItem(id=6, answerContent = "您目前尚無可供查詢的訂單，請先下單訂購喜歡的商品喔~"))

    private val anotherCustomerServiceItem = ChatBotDataItem.ForAnswerItem(AnswerItem(id=7, answerContent = "抱歉，目前沒有其他客服人員，是否服務不佳需進行申訴?"))

    private fun getStartChatBot(){
        dataList.add(helloItem)
        dataList.add(questionItem)

        _chatBotContentListForAdapter.value = dataList
    }

    val getDeliveryInfo = fun(){

        dataList.add(deliveryInfoItem)
        dataList.add(questionItem)

        _chatBotContentListForAdapter.value = dataList
    }

    val getProcessInfo = fun(){

        dataList.add(processInfoItem)
        dataList.add(questionItem)

        _chatBotContentListForAdapter.value = dataList
    }

    val getExchangeInfo = fun(){

        dataList.add(exchangeInfoItem)
        dataList.add(questionItem)

        _chatBotContentListForAdapter.value = dataList
    }

    val getOrderInfo = fun(){

        dataList.add(orderInfoItem)
        dataList.add(questionItem)

        _chatBotContentListForAdapter.value = dataList
    }

    val getFindAnotherCustomerService = fun(){

        dataList.add(anotherCustomerServiceItem)
        dataList.add(questionItem)

        _chatBotContentListForAdapter.value = dataList
    }


    init {
        getStartChatBot()
    }

}