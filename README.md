# ComponentBasedApp

### 组件化App配置一览
* *项目根目录gradle.properties配置:*
```IsBuildModule=false```
#### **build.gradle文件配置**

* *Library模块build.gradle配置：*
```
buildTypes {
    debug {
        buildConfigField "String", "ENV", "\"debug\""
        signingConfig signingConfigs.debug
    }
    release {
        buildConfigField "String", "ENV", "\"release\""
        minifyEnabled false
        proguardFiles getDefaultProguardFile('proguard-android.txt'), 'proguard-rules.pro'
    }
}
//需要加上该参数否则会提示debug not found
publishNonDefault true 

sourceSets {
    main {
        jniLibs.srcDirs = ['libs']
    }
}

dependencies {
    compile fileTree(dir: 'libs', include: ['*.jar'])
    compile 'com.android.support:appcompat-v7:25.3.1'
    //router
    compile 'com.github.mzule.activityrouter:activityrouter:1.2.2'
}
```
* *公共模块的build.gradle配置：*
```
//资源文件名前缀约束
resourcePrefix "base_"

dependencies {
    compile fileTree(dir: 'libs', include: ['*.jar'])
    //library依赖
    debugCompile project(path: ':Library', configuration: 'debug')
    releaseCompile project(path: ':Library', configuration: 'release')
    //router
    annotationProcessor 'com.github.mzule.activityrouter:compiler:1.1.7'
}
```
*  *业务模块的build.gradle配置：*
```
if (IsBuildModule.toBoolean()) {
    apply plugin: 'com.android.application'
} else {
    apply plugin: 'com.android.library'
}
```
```
//配置AndroidManifest.xml路径
sourceSets {
	main {
		if (IsBuildModule.toBoolean()) {
			manifest.srcFile 'src/main/debug/AndroidManifest.xml'
	    } else {
			manifest.srcFile 'src/main/release/AndroidManifest.xml'
        }
    }
}
//资源文件名前缀约束
resourcePrefix "modulea_"

dependencies {
    compile fileTree(dir: 'libs', include: ['*.jar'])
    compile project(':BaseModule')
    //router
    annotationProcessor 'com.github.mzule.activityrouter:compiler:1.1.7'
}
```
#### **Module类创建**
- 分别创建各模块下的Module类：
```
//在app/src/main/java/{packageName}/目录下创建
@Module("app")
public class AppModule {
}

//在Library/src/main/java/{packageName}/目录下创建
@Module("library")
public class Library {
}

//在BaseModule/src/main/java/{packageName}/目录下创建
@Module("baseModule")
public class BaseModule {
}

//在ModuleA/src/main/java/{packageName}/目录下创建
@Module("moduleA")
public class ModuleA {
}
```
- 创建Application类：
```
//把所有业务模块添加到依赖中
@Modules({"app", "moduleA"})
public class CommonApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
```
- 给使用到的Activity类添加别名（可传值），用于Routers寻址
```
@Router("AAA")
public class TestA extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modulea_testa);
    }
}

@Router("AAA/:id/:title")
public class TestA extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modulea_testa);
    }
}
```
#### **主项目下AndroidManifest.xml配置**
- 配置RouterActivity，*common*为自定义标识
```
<activity
    android:name="com.github.mzule.activityrouter.router.RouterActivity"
    android:theme="@android:style/Theme.NoDisplay">
    <intent-filter>
        <action android:name="android.intent.action.VIEW" />

        <category android:name="android.intent.category.DEFAULT" />
        <category android:name="android.intent.category.BROWSABLE" />

        <data android:scheme="common" />
    </intent-filter>
</activity>
```
- Router跳转
```
Routers.open(context, "common://AAA");
Routers.open(context, "common://AAA/13/这是标题");
```
