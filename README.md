# Billie Eilish - تطبيق الترفيه المتكامل

<div align="center">
  <img src="https://img.shields.io/badge/Version-0.01-blue.svg" alt="Version">
  <img src="https://img.shields.io/badge/Platform-Android-green.svg" alt="Platform">
  <img src="https://img.shields.io/badge/Language-Kotlin-purple.svg" alt="Language">
  <img src="https://img.shields.io/badge/UI-Jetpack%20Compose-orange.svg" alt="UI">
</div>

## 📱 نظرة عامة

تطبيق "Billie Eilish" هو تطبيق ترفيه متكامل وحديث مبني بتقنيات Android الحديثة. يوفر التطبيق تجربة شاملة لمشاهدة الأفلام والمسلسلات والأنمي والقنوات التلفزيونية المباشرة.

## ✨ الميزات الرئيسية

### 🎬 الأفلام والمسلسلات
- **مكتبة شاملة**: آلاف الأفلام والمسلسلات من TMDB API
- **جودة عالية**: دعم جودات متعددة للفيديو
- **معلومات تفصيلية**: تقييمات، ممثلين، تواريخ إصدار، وأوصاف شاملة
- **تصنيفات متنوعة**: أكشن، كوميديا، دراما، رعب، وأكثر

### 📺 القنوات التلفزيونية
- **بث مباشر**: مئات القنوات العربية والعالمية
- **مصدر موثوق**: قنوات من مشروع IPTV-org
- **تصنيف ذكي**: أخبار، رياضة، ترفيه، أطفال، وأكثر
- **جودة عالية**: بث عالي الجودة مع استقرار ممتاز

### 🎌 الأنمي
- **مجموعة واسعة**: أنمي ياباني وعالمي
- **حلقات منظمة**: تتبع الحلقات والمواسم
- **ترجمة عربية**: دعم الترجمة العربية والإنجليزية

### 🔐 المصادقة والحسابات
- **تسجيل دخول Google**: مصادقة آمنة وسريعة
- **Firebase**: حفظ آمن للبيانات والتفضيلات
- **مزامنة**: مزامنة البيانات عبر الأجهزة

### ⭐ المفضلات
- **قوائم شخصية**: إضافة المحتوى للمفضلة
- **تنظيم ذكي**: تصنيف المفضلات حسب النوع
- **وصول سريع**: الوصول السريع للمحتوى المفضل

## 🛠️ التقنيات المستخدمة

### Frontend
- **Kotlin**: لغة البرمجة الأساسية
- **Jetpack Compose**: واجهة المستخدم الحديثة
- **Material Design 3**: تصميم عصري ومتجاوب
- **Navigation Component**: تنقل سلس بين الشاشات

### Backend & APIs
- **TMDB API**: بيانات الأفلام والمسلسلات
- **SuperEmbed API**: تشغيل الفيديوهات
- **IPTV-org**: القنوات التلفزيونية المباشرة
- **Firebase**: المصادقة وقاعدة البيانات

### Architecture
- **MVVM Pattern**: هيكلة نظيفة ومنظمة
- **Repository Pattern**: فصل طبقات البيانات
- **Dependency Injection**: Hilt لإدارة التبعيات
- **Clean Architecture**: كود قابل للصيانة والتطوير

### Media & Networking
- **ExoPlayer**: مشغل فيديو متقدم
- **Retrofit**: شبكة HTTP موثوقة
- **Coil**: تحميل وعرض الصور
- **OkHttp**: عميل HTTP محسن

## 📁 هيكل المشروع

```
app/
├── src/main/java/com/billieeilish/app/
│   ├── data/                    # طبقة البيانات
│   │   ├── api/                # APIs والشبكة
│   │   ├── repository/         # تنفيذ المستودعات
│   │   └── model/              # نماذج البيانات
│   ├── domain/                 # طبقة المنطق
│   │   ├── model/              # نماذج المجال
│   │   ├── repository/         # واجهات المستودعات
│   │   └── usecase/            # حالات الاستخدام
│   ├── presentation/           # طبقة العرض
│   │   ├── auth/               # شاشات المصادقة
│   │   ├── movies/             # شاشات الأفلام
│   │   ├── series/             # شاشات المسلسلات
│   │   ├── anime/              # شاشات الأنمي
│   │   ├── tv/                 # شاشات القنوات
│   │   ├── favorites/          # شاشات المفضلات
│   │   ├── profile/            # شاشات الملف الشخصي
│   │   ├── player/             # مشغل الفيديو
│   │   └── main/               # الشاشة الرئيسية
│   ├── di/                     # حقن التبعيات
│   └── utils/                  # أدوات مساعدة
```

## 🚀 التثبيت والتشغيل

### المتطلبات
- Android Studio Arctic Fox أو أحدث
- Android SDK 24+ (Android 7.0)
- Kotlin 1.9.0+
- Gradle 8.4+

### خطوات التثبيت

1. **استنساخ المشروع**
```bash
git clone https://github.com/h85105645-art/revrvrd.git
cd revrvrd
```

2. **إعداد Firebase**
- إنشاء مشروع Firebase جديد
- إضافة ملف `google-services.json` إلى مجلد `app/`
- تفعيل Authentication و Firestore

3. **إعداد API Keys**
- الحصول على مفتاح TMDB API
- إضافة المفتاح في `local.properties`:
```properties
TMDB_API_KEY="your_tmdb_api_key_here"
```

4. **بناء التطبيق**
```bash
./gradlew assembleDebug
```

5. **تشغيل التطبيق**
```bash
./gradlew installDebug
```

## 🔧 الإعدادات

### Firebase Configuration
```json
{
  "project_info": {
    "project_id": "billie-eilish-app",
    "storage_bucket": "billie-eilish-app.appspot.com"
  }
}
```

### API Endpoints
- **TMDB**: `https://api.themoviedb.org/3/`
- **SuperEmbed**: `https://multiembed.mov/`
- **IPTV**: `https://iptv-org.github.io/iptv/`

## 📱 لقطات الشاشة

### الشاشة الرئيسية
- تصميم حديث مع تنقل سفلي
- عرض المحتوى المميز
- بحث سريع وفعال

### شاشة الأفلام
- شبكة عرض جذابة
- تصنيفات متعددة
- معلومات تفصيلية لكل فيلم

### مشغل الفيديو
- واجهة تحكم بديهية
- دعم جودات متعددة
- تحكم كامل في التشغيل

## 🤝 المساهمة

نرحب بالمساهمات! يرجى اتباع الخطوات التالية:

1. Fork المشروع
2. إنشاء branch جديد (`git checkout -b feature/amazing-feature`)
3. Commit التغييرات (`git commit -m 'Add amazing feature'`)
4. Push إلى Branch (`git push origin feature/amazing-feature`)
5. فتح Pull Request

## 📄 الترخيص

هذا المشروع مرخص تحت رخصة MIT - راجع ملف [LICENSE](LICENSE) للتفاصيل.

## 📞 التواصل

- **المطور**: [h85105645-art](https://github.com/h85105645-art)
- **البريد الإلكتروني**: developer@billieeilish.app
- **الموقع**: [billieeilish.app](https://billieeilish.app)

## 🙏 شكر وتقدير

- **TMDB**: لتوفير بيانات الأفلام والمسلسلات
- **IPTV-org**: لمشاركة قوائم القنوات التلفزيونية
- **Firebase**: لخدمات Backend الموثوقة
- **Jetpack Compose**: لإطار عمل UI الحديث

---

<div align="center">
  <p>صُنع بـ ❤️ للمجتمع العربي</p>
  <p>© 2024 Billie Eilish App. جميع الحقوق محفوظة.</p>
</div>