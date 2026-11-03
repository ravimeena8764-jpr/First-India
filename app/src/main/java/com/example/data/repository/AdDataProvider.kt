package com.example.data.repository

import com.example.data.model.DisplayBannerAd
import com.example.data.model.InArticleAd
import com.example.data.model.LocalCityAd
import com.example.data.model.NativeFeedAd
import com.example.data.model.VideoAd

object AdDataProvider {

    // 1. Display Banner Ads (Top / In-line leaderboard banners)
    val displayBannerAds = listOf(
        DisplayBannerAd(
            id = "banner_1",
            brandName = "मारुति सुजुकी एरीना",
            headline = "नई स्विफ्ट 2026: 25.75 km/l माइलेज का दम",
            subText = "राजस्थान के सभी शोरूम्स पर टेस्ट ड्राइव उपलब्ध। ₹45,000 तक का एक्सचेंज बोनस।",
            badgeText = "विज्ञापन | AD",
            bannerImageUrl = "https://images.unsplash.com/photo-1549399542-7e3f8b79c341?w=800&q=80",
            ctaText = "टेस्ट ड्राइव बुक करें",
            targetUrl = "https://marutisuzuki.com/swift",
            accentColorHex = 0xFFC62828
        ),
        DisplayBannerAd(
            id = "banner_2",
            brandName = "राजस्थान पर्यटन विभाग",
            headline = "पधारो म्हारे देश: विंटर हेरिटेज कार्निवल 2026",
            subText = "जैसलमेर, उदयपुर और जोधपुर के शाही महलों में विशेष पारिवारिक पैकेज पर 30% छूट।",
            badgeText = "पर्यटन प्रायोजित | AD",
            bannerImageUrl = "https://images.unsplash.com/photo-1599661046289-e31897846e41?w=800&q=80",
            ctaText = "पैकेज एक्सप्लोर करें",
            targetUrl = "https://tourism.rajasthan.gov.in",
            accentColorHex = 0xFFD84315
        ),
        DisplayBannerAd(
            id = "banner_3",
            brandName = "भारतीय स्टेट बैंक (SBI)",
            headline = "सपनों का आशियाना: एसबीआई होम लोन 8.40% से शुरू",
            subText = "शून्य प्रोसेसिंग शुल्क और तुरंत सैंक्शन। आसान मासिक किश्तों में अपना घर बनाएं।",
            badgeText = "फाइनेंस पार्टनर | AD",
            bannerImageUrl = "https://images.unsplash.com/photo-1560518883-ce09059eeffa?w=800&q=80",
            ctaText = "योग्यता चेक करें",
            targetUrl = "https://sbi.co.in/homeloans",
            accentColorHex = 0xFF1565C0
        )
    )

    // 2. Native Feed Ads (Blended seamlessly into news feed like standard news cards)
    val nativeFeedAds = listOf(
        NativeFeedAd(
            id = "native_1",
            sponsorName = "वंडर सीमेंट राजस्थान",
            title = "वंडर सीमेंट: एक परफेक्ट शुरुआत! जानिए भूकंप-रोधी और टिकाऊ घर निर्माण के 5 सुनहरे नियम",
            description = "घर बनाने से पहले सही सीमेंट और कंक्रीट का चुनाव अत्यंत आवश्यक है। वंडर सीमेंट की उन्नत जर्मन टेक्नोलॉजी दे आपकी नींव को दशकों की मजबूती। निशुल्क गाइड डाउनलोड करें।",
            badgeText = "प्रायोजित (Sponsored)",
            imageUrl = "https://images.unsplash.com/photo-1503387762-592deb58ef4e?w=800&q=80",
            logoUrl = "https://images.unsplash.com/photo-1541888946425-d0fbb186c5f7?w=120&q=80",
            rating = 4.9f,
            ctaText = "फ्री गाइड पढ़ें",
            landingUrl = "https://wondercement.com/home-building",
            categoryTag = "इंडस्ट्री एवं इंफ्रा"
        ),
        NativeFeedAd(
            id = "native_2",
            sponsorName = "एलन कॅरियर इंस्टिट्यूट कोटा",
            title = "IIT-JEE एवं NEET 2026: एलन कोटा के नए बैचेस में प्रवेश प्रारंभ, TALLENTEX से पाएं 90% तक स्कॉलरशिप",
            description = "देश की शीर्ष रैंकर्स देने वाली कोटा फैकल्टी के मार्गदर्शन में अपनी तैयारी को दें नई उड़ान। हाइब्रिड और क्लासरूम प्रोग्राम्स के लिए ऑनलाइन रजिस्ट्रेशन जारी।",
            badgeText = "प्रायोजित (Sponsored)",
            imageUrl = "https://images.unsplash.com/photo-1523240795612-9a054b0db644?w=800&q=80",
            logoUrl = "https://images.unsplash.com/photo-1534644107580-3a4dbd494a95?w=120&q=80",
            rating = 4.8f,
            ctaText = "स्कॉलरशिप टेस्ट दें",
            landingUrl = "https://allen.ac.in/admissions",
            categoryTag = "शिक्षा एवं कॅरियर"
        ),
        NativeFeedAd(
            id = "native_3",
            sponsorName = "रिलायंस डिजिटल राजस्थान",
            title = "इलेक्ट्रॉनिक्स का महाकुंभ: 4K स्मार्ट टीवी और 5G स्मार्टफोन्स पर 40% तक की भारी बचत!",
            description = "जयपुर, जोधपुर, कोटा और उदयपुर के सभी स्टोर्स में उपलब्ध। पुराने डिवाइस एक्सचेंज पर ₹5,000 का अतिरिक्त बोनस एवं नो-कॉस्ट EMI।",
            badgeText = "प्रायोजित (Sponsored)",
            imageUrl = "https://images.unsplash.com/photo-1593784991095-a205069470b6?w=800&q=80",
            logoUrl = "https://images.unsplash.com/photo-1526738549149-8e07eca6c147?w=120&q=80",
            rating = 4.7f,
            ctaText = "स्टोर ऑफर्स देखें",
            landingUrl = "https://reliancedigital.in",
            categoryTag = "टेक्नोलॉजी एवं गैजेट्स"
        )
    )

    // 3. In-Article Ads (Embedded inside article text while reading)
    val inArticleAds = listOf(
        InArticleAd(
            id = "in_art_1",
            brandName = "फोर्टिस एस्कॉर्ट्स हॉस्पिटल जयपुर",
            headline = "सम्पूर्ण हृदय एवं प्रिवेंटिव हेल्थ चेकअप मात्र ₹999 में",
            bodyText = "वरिष्ठ कॉर्डियोलॉजिस्ट से परामर्श, ईसीजी, लिपिड प्रोफाइल और ब्लड शुगर टेस्ट शामिल। अपने और परिवार के स्वास्थ्य की समय रहते जांच करवाएं।",
            badgeText = "पार्टनर हेल्थ संदेश | AD",
            imageUrl = "https://images.unsplash.com/photo-1519494026892-80bbd2d6fd0d?w=800&q=80",
            ctaText = "अपॉइंटमेंट बुक करें",
            promoCode = "FORTIS-HEART26",
            targetUrl = "https://fortishealthcare.com/jaipur"
        ),
        InArticleAd(
            id = "in_art_2",
            brandName = "अंबुजा प्लस कूल वॉल्स",
            headline = "गर्मियों में घर रहेगा 5 डिग्री तक ठंडा: अंबुजा प्लस",
            bodyText = "विशेष थर्मल इन्सुलेशन टेक्नोलॉजी जो बिजली के बिल में करे 25% तक की बचत। आज ही अपने नजदीकी ऑथराइज्ड डीलर से संपर्क करें।",
            badgeText = "प्रायोजित संदेश | AD",
            imageUrl = "https://images.unsplash.com/photo-1513694203232-719a280e022f?w=800&q=80",
            ctaText = "फ्री सैंपल मंगाएं",
            promoCode = "COOLWALL26",
            targetUrl = "https://ambujacement.com"
        ),
        InArticleAd(
            id = "in_art_3",
            brandName = "आवास फाइनेंसियर्स लिमिटेड",
            headline = "कच्चे या पक्के मकान के लिए आसान होम लोन: न्यूनतम पेपरवर्क",
            bodyText = "ग्रामीण एवं अर्ध-शहरी क्षेत्रों के लिए विशेष ऋण योजना। कोई इनकम प्रूफ अनिवार्य नहीं, 3 दिन में लोन की स्वीकृति।",
            badgeText = "फाइनेंशियल पार्टनर | AD",
            imageUrl = "https://images.unsplash.com/photo-1554224155-8d04cb21cd6c?w=800&q=80",
            ctaText = "तुरंत अप्लाई करें",
            promoCode = "AAVAS-EASY",
            targetUrl = "https://aavas.in"
        )
    )

    // 4. Local City Ads (Tailored to specific Rajasthan cities in MyCitiesScreen)
    val localCityAds = listOf(
        // Jaipur Ads
        LocalCityAd(
            id = "local_jpr_1",
            cityHindi = "जयपुर",
            businessName = "मंगलम ग्रुप रेजिडेंसी",
            categoryHindi = "🏢 रियल एस्टेट / लक्जरी फ्लैट्स",
            headline = "वैशाली नगर एक्सटेंशन में 3 BHK प्रीमियम फ्लैट्स: पजेशन रेडी",
            offerText = "क्लब हाउस, स्विमिंग पूल व रूफटॉप गार्डन युक्त। बुकिंग पर मॉड्यूलर किचन व एसी फ्री!",
            address = "प्लॉट नं 45, गांधी पथ वेस्ट, वैशाली नगर, जयपुर",
            phone = "+91 98290 12345",
            whatsappNumber = "919829012345",
            imageUrl = "https://images.unsplash.com/photo-1545324418-cc1a3fa10c00?w=800&q=80"
        ),
        LocalCityAd(
            id = "local_jpr_2",
            cityHindi = "जयपुर",
            businessName = "तनिष्क ज्वेलरी शोरूम",
            categoryHindi = "💎 ज्वेलरी & ब्राइडल कलेक्शन",
            headline = "जयपुर वेडिंग ज्वेलरी महोत्सव: मेकिंग चार्ज पर फ्लैट 20% ऑफ",
            offerText = "कुंदन, पोल्की और सॉलिटेयर डायमंड का राजस्थान में सबसे भव्य संग्रह। ओल्ड गोल्ड एक्सचेंज पर 100% वैल्यू।",
            address = "एम.आई. रोड, पांच बत्ती के पास, जयपुर",
            phone = "+91 141 2367890",
            whatsappNumber = "911412367890",
            imageUrl = "https://images.unsplash.com/photo-1515562141207-7a88fb7ce338?w=800&q=80"
        ),

        // Jodhpur Ads
        LocalCityAd(
            id = "local_jdh_1",
            cityHindi = "जोधपुर",
            businessName = "जोधपुर स्वीट्स & कैफे",
            categoryHindi = "🍽️ प्रसिद्ध मिष्ठान एवं नमकीन",
            headline = "शुद्ध देशी घी का शाही घेवर व गर्मागर्म मावा कचौरी",
            offerText = "सूर्यनगरी का 70 वर्षों का विश्वसनीय स्वाद। शहर भर में 30 मिनट में फ्री होम डिलीवरी।",
            address = "सोजती गेट मुख्य चौराहा, जोधपुर",
            phone = "+91 94141 55678",
            whatsappNumber = "919414155678",
            imageUrl = "https://images.unsplash.com/photo-1555396273-367ea4eb4db5?w=800&q=80"
        ),
        LocalCityAd(
            id = "local_jdh_2",
            cityHindi = "जोधपुर",
            businessName = "उम्मेद हेरिटेज विला प्रोजेक्ट",
            categoryHindi = "🏡 विला एवं रेजिडेंशियल प्लॉट्स",
            headline = "मंडोर रोड पर हेरिटेज स्टाइल 4 BHK विला: आधुनिक सुख-सुविधाएं",
            offerText = "जोधपुरी नक्काशीदार पत्थर का निर्माण, 24 घंटे सुरक्षा और गेटेड टाउनशिप।",
            address = "मंडोर बायपास, एयरफोर्स एरिया के पास, जोधपुर",
            phone = "+91 98280 99887",
            whatsappNumber = "919828099887",
            imageUrl = "https://images.unsplash.com/photo-1600585154340-be6161a56a0c?w=800&q=80"
        ),

        // Kota Ads
        LocalCityAd(
            id = "local_kta_1",
            cityHindi = "कोटा",
            businessName = "रेज़ोनेंस एडुवेंचर्स कोटा",
            categoryHindi = "📚 आईआईटी एवं मेडिकल कोचिंग",
            headline = "11वीं व 12वीं ड्रॉपर बैच: एडमिशन ओपन, विशेष हॉस्टल सुविधा",
            offerText = "सर्वश्रेष्ठ डाउट रिमूवल फैकल्टी और साप्ताहिक टेस्ट सीरीज। अर्ली बर्ड एडमिशन पर 15% फीस छूट।",
            address = "इंद्रप्रस्थ इंडस्ट्रियल एरिया, रोड नं 1, कोटा",
            phone = "+91 744 2777777",
            whatsappNumber = "917442777777",
            imageUrl = "https://images.unsplash.com/photo-1523050854058-8df90110c9f1?w=800&q=80"
        ),
        LocalCityAd(
            id = "local_kta_2",
            cityHindi = "कोटा",
            businessName = "सुधा मल्टीस्पेशलिटी हॉस्पिटल",
            categoryHindi = "🏥 स्वास्थ्य एवं आपातकालीन सेवाएं",
            headline = "कोटा का आधुनिकतम कार्डियक और ट्रॉमा सेंटर",
            offerText = "24 घंटे एम्बुलेंस, आईसीयू व डिजिटल सीटी-स्कैन सुविधा। आरजीएचएस एवं चिरंजीवी कार्ड धारकों का निशुल्क इलाज।",
            address = "तलवंडी मुख्य मार्ग, कोटा",
            phone = "+91 744 2405060",
            whatsappNumber = "917442405060",
            imageUrl = "https://images.unsplash.com/photo-1586773860418-d37222d8fce3?w=800&q=80"
        ),

        // Udaipur Ads
        LocalCityAd(
            id = "local_udp_1",
            cityHindi = "उदयपुर",
            businessName = "द अनंता लेक व्यू रिसॉर्ट",
            categoryHindi = "🏰 हेरिटेज रिसॉर्ट एवं डेस्टिनेशन वेडिंग",
            headline = "अरावली की पहाड़ियों के बीच शाही डेस्टिनेशन वेडिंग वेन्यू",
            offerText = "300 कमरों का भव्य विला रिसॉर्ट, बैंक्वेट हॉल व इन्फिनिटी पूल। विंटर कॉर्पोरेट कॉन्फ्रेंस पैकेज उपलब्ध।",
            address = "कोडियात रोड, सिसारमा, उदयपुर",
            phone = "+91 294 6600600",
            whatsappNumber = "912946600600",
            imageUrl = "https://images.unsplash.com/photo-1566073771259-6a8506099945?w=800&q=80"
        ),
        LocalCityAd(
            id = "local_udp_2",
            cityHindi = "उदयपुर",
            businessName = "मेवाड़ मोटर्स मारुति एरीना",
            categoryHindi = "🚗 ऑटोमोबाइल एवं सर्विस सेंटर",
            headline = "उदयपुर में ग्रैंड विटारा हाइब्रिड पर तुरंत डिलीवरी",
            offerText = "शून्य डाउन पेमेंट विकल्प और 5 साल की फ्री वारंटी। अपनी पुरानी कार का तुरंत वैल्यूएशन करवाएं।",
            address = "बलीचा बाईपास, नेशनल हाईवे 8, उदयपुर",
            phone = "+91 94140 12389",
            whatsappNumber = "919414012389",
            imageUrl = "https://images.unsplash.com/photo-1580273916550-e323be2ae537?w=800&q=80"
        ),

        // Bikaner Ads
        LocalCityAd(
            id = "local_bkn_1",
            cityHindi = "बीकानेर",
            businessName = "बीकाजी ओरिजिनल नमकीन",
            categoryHindi = "🥨 परंपरागत बीकानेरी भुजिया एवं स्वीट्स",
            headline = "असली बीकानेरी भुजिया, रसगुल्ला एवं काजू कतली",
            offerText = "स्पेशल गिफ्ट हैंपर्स और कॉर्पोरेट पैक्स पर विशेष छूट। ऑल इंडिया एक्सप्रेस कूरियर उपलब्ध।",
            address = "स्टेशन रोड, कोटगेट के सामने, बीकानेर",
            phone = "+91 151 2525555",
            whatsappNumber = "911512525555",
            imageUrl = "https://images.unsplash.com/photo-1509358271058-acd22cc93898?w=800&q=80"
        ),

        // Ajmer Ads
        LocalCityAd(
            id = "local_ajm_1",
            cityHindi = "अजमेर",
            businessName = "पुष्कर रॉयल डेजर्ट कैंप्स",
            categoryHindi = "⛺ टेंट रिसॉर्ट एवं कैमल सफारी",
            headline = "पुष्कर के धोरों में लग्जरी स्विस टेंट का अद्भुत अनुभव",
            offerText = "राजस्थानी लोक नृत्य, कालबेलिया प्रस्तुति, बोनफायर व पारंपरिक राजस्थानी डिनर बुफे शामिल।",
            address = "मेला मैदान के पास, पुष्कर, अजमेर",
            phone = "+91 98292 44332",
            whatsappNumber = "919829244332",
            imageUrl = "https://images.unsplash.com/photo-1544644181-1484b3fdfc62?w=800&q=80"
        )
    )

    // 5. Video Ads (Pre-Roll & Mid-Roll interactive video commercials)
    val videoPreRollAd = VideoAd(
        id = "video_ad_preroll_1",
        brandName = "टाटा मोटर्स पैसेंजर व्हीकल्स",
        headline = "नया नेक्सॉन.ईवी: 465 किमी की लॉन्ग रेंज",
        subHeadline = "भारत की नंबर 1 इलेक्ट्रिक एसयूवी। शून्य ध्वनि, 100% पावर।",
        durationSeconds = 15,
        skippableAfterSeconds = 5,
        videoThumbnailUrl = "https://images.unsplash.com/photo-1563720223185-11003d516935?w=1000&q=80",
        ctaText = "वेबसाइट पर जाएं",
        actionUrl = "https://ev.tatamotors.com/nexon-ev",
        isMidRoll = false
    )

    val videoMidRollAd = VideoAd(
        id = "video_ad_midroll_1",
        brandName = "फर्स्ट इंडिया गोल्ड प्रीमियम क्लब",
        headline = "विज्ञापन-मुक्त समाचार और डिजिटल ई-पेपर का पूरा संग्रह",
        subHeadline = "मात्र ₹49/माह में पाएं एक्सक्लूसिव स्पेशल इन्वेस्टिगेटिव रिपोर्ट्स और लाइव टीवी पॉडकास्ट।",
        durationSeconds = 12,
        skippableAfterSeconds = 4,
        videoThumbnailUrl = "https://images.unsplash.com/photo-1504711434969-e33886168f5c?w=1000&q=80",
        ctaText = "प्रीमियम जॉइन करें",
        actionUrl = "https://firstindianews.com/premium",
        isMidRoll = true,
        midRollCueTimeSeconds = 12
    )

    // Helper: get local ads for a city (or return Jaipur as default if city not found)
    fun getLocalAdsForCity(cityHindi: String): List<LocalCityAd> {
        val matches = localCityAds.filter { it.cityHindi.equals(cityHindi, ignoreCase = true) }
        return if (matches.isNotEmpty()) matches else localCityAds.filter { it.cityHindi == "जयपुर" }
    }
}
