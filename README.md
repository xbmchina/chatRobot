# chatRobot
This is a chat robot base on turling robot.

if you have a problem about the gradle,you should look at the file which is build.gradle.

you should add the content as following:

    android {
        packagingOptions {
            exclude 'META-INF/DEPENDENCIES.txt'
            exclude 'META-INF/LICENSE.txt'
            exclude 'META-INF/NOTICE.txt'
            exclude 'META-INF/NOTICE'
            exclude 'META-INF/LICENSE'
            exclude 'META-INF/DEPENDENCIES'
            exclude 'META-INF/notice.txt'
            exclude 'META-INF/license.txt'
            exclude 'META-INF/dependencies.txt'
            exclude 'META-INF/LGPL2.1'
        }
    }
}




dependencies {
    compile fileTree(dir: 'libs', include: ['*.jar'])
    compile 'com.android.support:appcompat-v7:23.0.0'
    compile files('libs/commons-codec-1.9.jar')
    compile files('libs/commons-logging-1.2.jar')
    compile files('libs/fluent-hc-4.5.2.jar')
    compile files('libs/httpclient-4.5.2.jar')
    compile files('libs/httpclient-cache-4.5.2.jar')
    compile files('libs/httpclient-win-4.5.2.jar')
    compile files('libs/httpcore-4.4.4.jar')
    compile files('libs/httpmime-4.5.2.jar')
    compile files('libs/jna-4.1.0.jar')
    compile files('libs/jna-platform-4.1.0.jar')
}

