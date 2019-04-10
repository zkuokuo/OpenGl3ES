#include "FileUtil.h"

AAssetManager *FileUtil::aam;//指向AAssetmanager对象指针
//设置AAssetManager对象指针
void FileUtil::setAAssetManager(AAssetManager *aamIn) {
    aam = aamIn;
}

string FileUtil::loadShaderStr(string fname)//加载着色器文件
{
    AAsset* asset = AAssetManager_open(aam, fname.c_str(), AASSET_MODE_UNKNOWN);//创建AAsset对象
    off_t fileSize = AAsset_getLength(asset);//获取AAsset对象的长度
    unsigned char *data = (unsigned char *) malloc(fileSize + 1);//分配内存
    data[fileSize] = '\0';//将最后一个字符设置为'\0'
    int size = AAsset_read(asset, (void *) data, fileSize);//读取文件大小
    std::string resultStr((const char *) data);//获得结果字符串

    return resultStr;
}

