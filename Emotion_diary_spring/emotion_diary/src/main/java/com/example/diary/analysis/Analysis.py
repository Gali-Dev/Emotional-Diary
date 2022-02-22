# -*- coding: utf-8 -*-
import sys
sys.path.append("C:\ProgramData\Anaconda3\Lib\site-packages")

import konlpy
from collections import Counter
from konlpy.tag import Okt
import matplotlib.pyplot as plt
from matplotlib import font_manager, rc
import pytagcloud
import seaborn as sns
import pandas as pd
import numpy as np
from math import pi
from matplotlib.path import Path
from matplotlib.spines import Spine
from matplotlib.transforms import Affine2D

def testFunc(a,b):
    print("TEST FUNC")
    c = a+b
    return c

# 감정사전 불러오기


def load_emolex(emolex):
    emolex_df = pd.read_excel(emolex)
    return emolex_df

# 감정 사전에서 감정 간의 상관관계


def corr_emotion(emolex_df):
    f, ax = plt.subplots(figsize=(20, 20))
    sns.heatmap(emolex_df.corr(), annot=True, linewidths=0.6, fmt=".4f", ax=ax)
    plt.show()

# 게시글 하나 불러와서 형태소 분석


def load_content(board):
    target = board.contents
    okt = Okt()
    nouns = okt.nouns(target)
    return nouns

# 형태소 분석한 결과 가져와서 워드 클라우드 생성


def wordcloud(nouns,root,board):
    count = Counter(nouns)
    tag = count.most_common(30)
    taglist = pytagcloud.make_tags(tag)
    save = root+"wordcloud"+board.bno
    pytagcloud.create_tag_image(taglist, save,
                                size=(900, 600), fontname='Korean', rectangular=False)

# 감정사전의 단어와 분석할 대상의 명사가 같은 것들을 뽑아 데이터프레임 생성


def emo_dataframe(nouns, emolex_df):
    analysis_df = emolex_df[emolex_df["Korean (ko)"].isin(nouns)]
    # 감정별로 합산
    emotion_sum = analysis_df[["Positive", "Negative", "Anger", "Anticipation",
                               "Disgust", "Fear", "Joy", "Sadness", "Surprise", "Trust"]].sum()
    return emotion_sum

# 감정분석 결과 바 그래프


def bar_graph(emotion_sum,root,board):
    x = ["Positive", "Negative", "Anger", "Anticipation",
         "Disgust", "Fear", "Joy", "Sadness", "Surprise", "Trust"]
    plt.bar(x, emotion_sum, width=0.3, color='blue')
    plt.savefig(root+"bar"+board.bno)

# 선그래프


def line_graph(emotion_sum,root,board):
    x = ["Positive", "Negative", "Anger", "Anticipation",
         "Disgust", "Fear", "Joy", "Sadness", "Surprise", "Trust"]
    plt.plot(x, emotion_sum)
    plt.savefig(root+"line"+board.bno)

# 파이그래프


def pie_graph(emotion_sum,root,board):
    x = ["Positive", "Negative", "Anger", "Anticipation",
         "Disgust", "Fear", "Joy", "Sadness", "Surprise", "Trust"]
    plt.pie(emotion_sum, labels=x, autopct='%.1f%%')
    plt.savefig(root+"pie"+board.bno)

# 레이더 그래프


def raider_graph(emotion_sum):
    result_df = pd.DataFrame(emotion_sum).T
    labels = result_df.columns[0:]
    num_labels = len(labels)
    angles = [x/float(num_labels)*(2*pi) for x in range(num_labels)]
    angles += angles[:1]
    my_palette = plt.cm.get_cmap("Set2", len(result_df.index))
    fig = plt.figure(figsize=(20, 20))
    fig.set_facecolor('white')

    for i, row in result_df.iterrows():
        color = my_palette(i)
        data = result_df.iloc[i].tolist()
        data += data[:1]

        ax = plt.subplot(3, 2, i+1, polar=True)
        ax.set_theta_offset(pi / 2)  # 시작점
        ax.set_theta_direction(-1)  # 그려지는 방향 시계방향

        plt.xticks(angles[:-1], labels, fontsize=13)  # x축 눈금 라벨
        ax.tick_params(axis='x', which='major', pad=15)  # x축과 눈금 사이에 여백을 준다.

        ax.set_rlabel_position(0)  # y축 각도 설정(degree 단위)
        plt.yticks([0, 2, 4, 6, 8, 10, 12, 14, 16, 18, 20, 22, 24, 26, 28, 30], ['0', '2', '4', '6', '8',
                                                                                 '10', '12', '14', '16', '18', '20', '22', '24', '26', '28', '30'], fontsize=10)  # y축 눈금 설정
        plt.ylim(0, 30)

        ax.plot(angles, data, color=color, linewidth=2,
                linestyle='solid')  # 레이더 차트 출력
        ax.fill(angles, data, color=color, alpha=0.4)  # 도형 안쪽에 색을 채워준다.

        # 타이틀은 캐릭터 클래스로 한다.
        plt.title('test', size=20, color=color, x=-0.2, y=1.2, ha='left')

    plt.tight_layout(pad=5)  # subplot간 패딩 조절
    plt.show()

    # 게시글 하나 분석
    def analysis_one_content(board, emolex, root):
        # 게시글 호출, 형태소 분석
        nouns = load_content(board)
        # 워드클라우드 생성
        wordcloud(root,board)
        # 감정사전 호출
        emolex_df = load_emolex(emolex)
        # 감정사전으로 분석
        emotion_sum = emotion_sum(nouns, emolex_df)
        # 바 그래프
        bar_graph(emotion_sum,root,board)
        # 파이 그래프
        pie_graph(emotion_sum,root,board)
        # 선그래프
        line_graph(emotion_sum,root,board)
        # 레이더 그래프
        raider_graph(emotion_sum,root,board)
        
