from django.db.models import Q
from django.http import HttpResponse
from django.shortcuts import render
from django.views import View
from rest_framework.response import Response
from rest_framework.views import APIView
from rest_framework import status
import os

from . import analysisPro
from .serializers import BoardSerializer

# Create your views here.
from api.models import Board

BASE_DIR = os.path.dirname(os.path.dirname(os.path.abspath(__file__)))


def load_emolex(request):
    emolex = BASE_DIR + "/api/static/emolex.xlsx"
    emo_df = analysisPro.load_emolex(emolex)
    # dir = BASE_DIR+"/api/static/main_corr.png"
    # analysisPro.corr_emotion(emo_df,dir)
    return HttpResponse("emolex load")

class main(APIView):

    def post(self, request, *args, **kwargs):
        contents = []
        for row in request.data:
            contents.append(row["contents"])
        emolex = BASE_DIR + "/api/static/emolex.xlsx"
        # 사전 호출
        emo_df = analysisPro.load_emolex(emolex)
        saveDIR = BASE_DIR + "/api/static/"
        # contents 형태소 분석
        nouns = analysisPro.group_content(contents)
        # 감정 분석
        emotion_sum = analysisPro.emo_dataframe(nouns, emo_df)
        # 워드클라우드
        analysisPro.wordcloud(nouns, saveDIR, 0)
        # 바 그래프
        analysisPro.bar_graph(emotion_sum, saveDIR, 0)
        # 선 그래프
        analysisPro.line_graph(emotion_sum, saveDIR, 0)
        # 파이 그래프
        analysisPro.pie_graph(emotion_sum, saveDIR, 0)
        # 레이더 그래프
        analysisPro.raider_graph(emotion_sum, saveDIR, 0)

        return Response({"result":"success"},status=status.HTTP_200_OK)


class analysisOne(APIView):
    model = Board
    serializer = BoardSerializer

    def get(self, request, *args, **kwargs):
        data = "get"
        return Response(data, status=200)

    def post(self, request, *args, **kwargs):
        serializer = self.serializer(data=request.data)
        serializer.is_valid(raise_exception=True)
        board = Board(bno=request.data["bno"], title=request.data["title"], contents=request.data["contents"])
        emolex = BASE_DIR + "/api/static/emolex.xlsx"
        # 사전 호출
        emo_df = analysisPro.load_emolex(emolex)
        saveDIR = BASE_DIR + "/api/static/board/"
        # contents 형태소 분석
        nouns = analysisPro.load_content(board)
        # 워드클라우드
        analysisPro.wordcloud(nouns, saveDIR, board.bno)
        # 감정 분석
        emotion_sum = analysisPro.emo_dataframe(nouns, emo_df)
        # 바 그래프
        analysisPro.bar_graph(emotion_sum, saveDIR, board.bno)
        # 선 그래프
        analysisPro.line_graph(emotion_sum, saveDIR, board.bno)
        # 파이 그래프
        analysisPro.pie_graph(emotion_sum, saveDIR, board.bno)
        # 레이더 그래프
        analysisPro.raider_graph(emotion_sum, saveDIR, board.bno)

        return Response({"result":"success"},status=status.HTTP_200_OK)


class saveBoard(APIView):
    def post(self, request, *args, **kwargs):
        board = Board(title=request.data["title"], contents=request.data["contents"])
        emolex = BASE_DIR + "/api/static/emolex.xlsx"
        emo_df = analysisPro.load_emolex(emolex)
        # contents 형태소 분석
        nouns = analysisPro.load_content(board)
        emotion = analysisPro.emo_board(nouns, emo_df)
        return Response({"emotion":emotion}, status=status.HTTP_200_OK)

class analysisGroup(APIView):
    def post(self, request,pk=None, *args, **kwargs):
        contents = []
        for row in request.data:
            contents.append(row["contents"])
        emolex = BASE_DIR + "/api/static/emolex.xlsx"
        # 사전 호출
        emo_df = analysisPro.load_emolex(emolex)
        saveDIR = BASE_DIR + "/api/static/together/"
        # contents 형태소 분석
        nouns = analysisPro.group_content(contents)
        # 감정 분석
        emotion_sum = analysisPro.emo_dataframe(nouns, emo_df)
        # 워드클라우드
        analysisPro.wordcloud(nouns, saveDIR, pk)
        # 바 그래프
        analysisPro.bar_graph(emotion_sum, saveDIR, pk)
        # 선 그래프
        analysisPro.line_graph(emotion_sum, saveDIR, pk)
        # 파이 그래프
        analysisPro.pie_graph(emotion_sum, saveDIR, pk)
        # 레이더 그래프
        analysisPro.raider_graph(emotion_sum, saveDIR, pk)

        return Response({"result":"success"},status=status.HTTP_200_OK)

class analysisMy(APIView):
    def post(self, request, pk=None, *args, **kwargs):
        contents = []
        for row in request.data:
            contents.append(row["contents"])
        emolex = BASE_DIR + "/api/static/emolex.xlsx"
        # 사전 호출
        emo_df = analysisPro.load_emolex(emolex)
        saveDIR = BASE_DIR + "/api/static/my/"
        # contents 형태소 분석
        nouns = analysisPro.group_content(contents)
        # 감정 분석
        emotion_sum = analysisPro.emo_dataframe(nouns, emo_df)
        # 워드클라우드
        analysisPro.wordcloud(nouns, saveDIR, pk)
        # 바 그래프
        analysisPro.bar_graph(emotion_sum, saveDIR, pk)
        # 선 그래프
        analysisPro.line_graph(emotion_sum, saveDIR, pk)
        # 파이 그래프
        analysisPro.pie_graph(emotion_sum, saveDIR, pk)
        # 레이더 그래프
        analysisPro.raider_graph(emotion_sum, saveDIR, pk)

        return Response({"result":"success"},status=status.HTTP_200_OK)