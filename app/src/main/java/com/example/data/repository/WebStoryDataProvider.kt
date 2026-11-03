package com.example.data.repository

import com.example.data.model.WebStory
import com.example.data.model.WebStorySlide

object WebStoryDataProvider {

    fun getWebStories(): List<WebStory> {
        return listOf(
            WebStory(
                id = "story_1",
                title = "राजस्थान के 5 विश्वप्रसिद्ध किले",
                category = "पर्यटन",
                coverImageUrl = "https://images.unsplash.com/photo-1599661046289-e31897846e41?w=800&q=80",
                slidesCount = 5,
                slides = listOf(
                    WebStorySlide(
                        id = "s1_1",
                        title = "आमेर का किला, जयपुर",
                        description = "यूनेस्को विश्व धरोहर में शामिल आमेर किला अपनी हिंदू-मुगल स्थापत्य कला और शीश महल के लिए दुनिया भर में प्रसिद्ध है।",
                        imageUrl = "https://images.unsplash.com/photo-1599661046289-e31897846e41?w=800&q=80",
                        tag = "जयपुर"
                    ),
                    WebStorySlide(
                        id = "s1_2",
                        title = "मेहरानगढ़ किला, जोधपुर",
                        description = "400 फीट ऊंची पहाड़ी पर स्थित मेहरानगढ़ किला जोधपुर का गौरव है, जहां से पूरा ब्लू सिटी नजर आता है।",
                        imageUrl = "https://images.unsplash.com/photo-1582510003544-4d00b7f74220?w=800&q=80",
                        tag = "जोधपुर"
                    ),
                    WebStorySlide(
                        id = "s1_3",
                        title = "जैसलमेर का सोनार किला",
                        description = "थार रेगिस्तान में पीले बलुआ पत्थर से बना यह किला दुनिया के गिने-चुने 'जीवंत किलों' में से एक है।",
                        imageUrl = "https://images.unsplash.com/photo-1609137144822-2646c827c13f?w=800&q=80",
                        tag = "जैसलमेर"
                    ),
                    WebStorySlide(
                        id = "s1_4",
                        title = "चित्तौड़गढ़ किला",
                        description = "शौर्य और बलिदान का प्रतीक चित्तौड़गढ़ किला भारत का सबसे बड़ा दुर्ग है, जहां विजय स्तंभ और कीर्ति स्तंभ स्थित हैं।",
                        imageUrl = "https://images.unsplash.com/photo-1600100397608-f010e42e4e1a?w=800&q=80",
                        tag = "चित्तौड़गढ़"
                    ),
                    WebStorySlide(
                        id = "s1_5",
                        title = "कुंभलगढ़ दुर्ग, राजसमंद",
                        description = "चीन की दीवार के बाद दुनिया की दूसरी सबसे लंबी दीवार (36 किमी) कुंभलगढ़ दुर्ग की ही है।",
                        imageUrl = "https://images.unsplash.com/photo-1590050752117-238cb0fb12b1?w=800&q=80",
                        tag = "कुंभलगढ़"
                    )
                ),
                viewsCount = "38K"
            ),
            WebStory(
                id = "story_2",
                title = "सोलर हब बना राजस्थान: 5 बड़े बदलाव",
                category = "विकास",
                coverImageUrl = "https://images.unsplash.com/photo-1509391365360-2e959784a276?w=800&q=80",
                slidesCount = 4,
                slides = listOf(
                    WebStorySlide(
                        id = "s2_1",
                        title = "भड़ला सोलर पार्क",
                        description = "जोधपुर का भड़ला सोलर पार्क 2245 मेगावाट क्षमता के साथ दुनिया के सबसे बड़े सौर पार्कों में से एक है।",
                        imageUrl = "https://images.unsplash.com/photo-1509391365360-2e959784a276?w=800&q=80",
                        tag = "जोधपुर"
                    ),
                    WebStorySlide(
                        id = "s2_2",
                        title = "रेगिस्तान बना ऊर्जा का पावरहाउस",
                        description = "जैसलमेर और बाड़मेर की तपती धूप अब स्वच्छ बिजली बनकर देश के लाखों घरों को रोशन कर रही है।",
                        imageUrl = "https://images.unsplash.com/photo-1497435334941-8c899ee9e8e9?w=800&q=80",
                        tag = "जैसलमेर"
                    ),
                    WebStorySlide(
                        id = "s2_3",
                        title = "किसानों को पीएम-कुसुम योजना का लाभ",
                        description = "खेतों में सोलर पंप लगने से बिजली बिल शून्य हुआ और दिन में फसलों की सिंचाई संभव हुई।",
                        imageUrl = "https://images.unsplash.com/photo-1542601906990-b4d3fb778b09?w=800&q=80",
                        tag = "कृषि"
                    ),
                    WebStorySlide(
                        id = "s2_4",
                        title = "हरित रोजगार में भारी उछाल",
                        description = "सोलर इंस्टॉलेशन और मेंटेनेंस में राज्य के हजारों तकनीकी डिप्लोमा धारकों को नौकरियां मिल रही हैं।",
                        imageUrl = "https://images.unsplash.com/photo-1466611653911-95081537e5b7?w=800&q=80",
                        tag = "रोजगार"
                    )
                ),
                viewsCount = "52K"
            ),
            WebStory(
                id = "story_3",
                title = "T20 वर्ल्ड कप: टीम इंडिया के उभरते 4 सितारे",
                category = "खेल",
                coverImageUrl = "https://images.unsplash.com/photo-1531415074868-036b107e775a?w=800&q=80",
                slidesCount = 4,
                slides = listOf(
                    WebStorySlide(
                        id = "s3_1",
                        title = "यशस्वी जायसवाल का विस्फोटक अंदाज",
                        description = "पावरप्ले में आक्रामक बल्लेबाजी और शतक लगाने की काबिलियत यशस्वी को सबसे खास ओपनर बनाती है।",
                        imageUrl = "https://images.unsplash.com/photo-1531415074868-036b107e775a?w=800&q=80",
                        tag = "क्रिकेट"
                    ),
                    WebStorySlide(
                        id = "s3_2",
                        title = "रिंकू सिंह: नए फिनिशर किंग",
                        description = "अंतिम ओवरों में 200+ के स्ट्राइक रेट से मैच खत्म करने का हुनर रिंकू को टीम इंडिया का ट्रंप कार्ड बनाता है।",
                        imageUrl = "https://images.unsplash.com/photo-1540747913346-19e32dc3e97e?w=800&q=80",
                        tag = "फिनिशर"
                    ),
                    WebStorySlide(
                        id = "s3_3",
                        title = "अर्शदीप सिंह की स्विंग और यॉर्कर",
                        description = "डेथ ओवर्स में पिनपॉइंट यॉर्कर डालने में माहिर अर्शदीप गेंदबाजी आक्रमण के प्रमुख स्तंभ हैं।",
                        imageUrl = "https://images.unsplash.com/photo-1587280501635-68a0e82cd5ff?w=800&q=80",
                        tag = "गेंदबाजी"
                    ),
                    WebStorySlide(
                        id = "s3_4",
                        title = "कुलदीप यादव की जादुई फिरकी",
                        description = "मिडिल ओवर्स में विपक्षी बल्लेबाजों को फंसाने और रन गति रोकने में कुलदीप का कोई सानी नहीं।",
                        imageUrl = "https://images.unsplash.com/photo-1512719994953-eabf50895df7?w=800&q=80",
                        tag = "स्पिनर"
                    )
                ),
                viewsCount = "64K"
            ),
            WebStory(
                id = "story_4",
                title = "भीषण गर्मी में सेहतमंद रहने के 4 देसी नुस्खे",
                category = "स्वास्थ्य",
                coverImageUrl = "https://images.unsplash.com/photo-1544367567-0f2fcb009e0b?w=800&q=80",
                slidesCount = 4,
                slides = listOf(
                    WebStorySlide(
                        id = "s4_1",
                        title = "कच्ची कैरी का पन्ना",
                        description = "लू और डिहाइड्रेशन से बचने के लिए भुने जीरे और पुदीने से बना आम पन्ना सबसे रामबाण उपाय है।",
                        imageUrl = "https://images.unsplash.com/photo-1550547660-d9450f859349?w=800&q=80",
                        tag = "आयुर्वेद"
                    ),
                    WebStorySlide(
                        id = "s4_2",
                        title = "सत्तू का शरबत",
                        description = "प्रोटीन और फाइबर से भरपूर भुने चने का सत्तू पेट को ठंडा रखता है और तुरंत ऊर्जा प्रदान करता है।",
                        imageUrl = "https://images.unsplash.com/photo-1512621776951-a57141f2eefd?w=800&q=80",
                        tag = "हेल्थ"
                    ),
                    WebStorySlide(
                        id = "s4_3",
                        title = "ताज़ा नारियल पानी",
                        description = "प्राकृतिक इलेक्ट्रोलाइट्स से भरपूर नारियल पानी शरीर में मिनरल्स का संतुलन बनाए रखता है।",
                        imageUrl = "https://images.unsplash.com/photo-1505576399279-565b52d4ac71?w=800&q=80",
                        tag = "हाइड्रेशन"
                    ),
                    WebStorySlide(
                        id = "s4_4",
                        title = "गोंद कतीरा का सेवन",
                        description = "दूध या फालूदा में भीगा हुआ गोंद कतीरा खाने से शरीर की आंतरिक गर्मी और जलन शांत होती है।",
                        imageUrl = "https://images.unsplash.com/photo-1544367567-0f2fcb009e0b?w=800&q=80",
                        tag = "देसी नुस्खा"
                    )
                ),
                viewsCount = "29K"
            ),
            WebStory(
                id = "story_5",
                title = "AI टूल्स 2026: जो आसान बना देंगे काम",
                category = "टेक",
                coverImageUrl = "https://images.unsplash.com/photo-1677442136019-21780ecad995?w=800&q=80",
                slidesCount = 4,
                slides = listOf(
                    WebStorySlide(
                        id = "s5_1",
                        title = "मल्टीमॉडल AI असिस्टेंट्स",
                        description = "आवाज, इमेज और वीडियो देखकर रियल-टाइम में जटिल समस्याओं का समाधान प्रस्तुत करते हैं।",
                        imageUrl = "https://images.unsplash.com/photo-1677442136019-21780ecad995?w=800&q=80",
                        tag = "AI"
                    ),
                    WebStorySlide(
                        id = "s5_2",
                        title = "ऑटोमेटेड कोडिंग व डिबगिंग",
                        description = "सॉफ्टवेयर डेवलपर्स अब घंटों का काम मिनटों में पूरा कर रहे हैं और कोड क्वालिटी बेहतर हो रही है।",
                        imageUrl = "https://images.unsplash.com/photo-1555066931-4365d14bab8c?w=800&q=80",
                        tag = "टेक"
                    ),
                    WebStorySlide(
                        id = "s5_3",
                        title = "स्मार्ट कंटेंट व ग्राफिक जनरेशन",
                        description = "सोशल मीडिया पोस्ट, वीडियो एडिट और क्रिएटिव प्रेजेंटेशन अब चंद सेकंड में तैयार हो जाते हैं।",
                        imageUrl = "https://images.unsplash.com/photo-1618005182384-a83a8bd57fbe?w=800&q=80",
                        tag = "डिजाइन"
                    ),
                    WebStorySlide(
                        id = "s5_4",
                        title = "रियल-टाइम वॉयस ट्रांसलेशन",
                        description = "हिंदी से 50+ विदेशी भाषाओं में बातचीत करना अब पलक झपकते ही संभव हो चुका है।",
                        imageUrl = "https://images.unsplash.com/photo-1589254065878-42c9da997008?w=800&q=80",
                        tag = "भाषा"
                    )
                ),
                viewsCount = "41K"
            )
        )
    }
}
