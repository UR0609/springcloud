package com.ljryh.svm.config.construct;

/**
 * @author ljryh
 * @date 2021/11/22 17:10
 */
public class Constant {

    // 词语出现的词频
    public static final String SVM_DATA_COUNT = "svm:data:count:";
    // 词语索引
    public static final String SVM_DATA = "svm:data:";
    // 词语索引 当前序号
    public static final String SVM_COUNT = "svm:count";
    // 词袋总量
    public static final String SVM_TOTAL_COUNT = "svm:total:count";
    // 意图名称
    public static final String SVM_INTENTION_DATA = "svm:intention:data:";
    // 意图 当前序号
    public static final String SVM_INTENTION_COUNT = "svm:intention:count";

    public static final Integer SVM_EXCEL = 1;
    public static final Integer SVM_TXT = 2;

    /**
     * https://www.csie.ntu.edu.tw/~cjlin/libsvm/
     * https://scikit-learn.org/stable/modules/generated/sklearn.svm.SVC.html
     * https://blog.csdn.net/zaishijizhidian/article/details/89365733
     */

    /**
     * C-SVC的惩罚参数C?默认值是1.0
     * C越大，相当于惩罚松弛变量，希望松弛变量接近0，即对误分类的惩罚增大，趋向于对训练集全分对的情况，
     * 这样对训练集测试时准确率很高，但泛化能力弱。
     * C值小，对误分类的惩罚减小，允许容错，将他们当成噪声点，泛化能力较强。
     */
    public static final Integer PRM_C = 1000;
    /**
     * 设置终止标准的容差（默认 0.001）
     */
    public static final double PRM_EPS = 0.001;
    /**
     * 在核函数中设置 gamma (默认 1/num_features)
     * gamma是选择RBF函数作为kernel后，该函数自带的一个参数。
     * 隐含地决定了数据映射到新的特征空间后的分布，gamma越大，支持向量越少，gamma值越小，支持向量越多。支持向量的个数影响训练与预测的速度。
     * 这里面大家需要注意的就是gamma的物理意义，大家提到很多的RBF的幅宽，它会影响每个支持向量对应的高斯的作用范围，从而影响泛化性能。
     * 如果gamma设的太大，方差会很小，方差很小的高斯分布长得又高又瘦， 会造成只会作用于支持向量样本附近，对于未知样本分类效果很差，
     * 存在训练准确率可以很高，(如果让方差无穷小，则理论上，高斯核的SVM可以拟合任何非线性数据，但容易过拟合)而测试准确率不高的可能，
     * 就是通常说的过训练；而如果设的过小，则会造成平滑效应太大，无法在训练集上得到特别高的准确率，也会影响测试集的准确率。
     */
    public static final Integer PRM_GAMMA = 10;
    /**
     * 是否训练SVC或SVR模型进行概率估计，0或1（默认0）
     */
    public static final Integer PRM_PROBABILITY = 1;
    /**
     * 以 MB 为单位设置缓存内存大小（默认 100）
     */
    public static final Integer PRM_CACHE_SIZE = 1024;



}
