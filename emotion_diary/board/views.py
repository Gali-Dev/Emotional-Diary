from django.shortcuts import render
from .models import Post

# Create your views here.


def main(request):
    return render(request, 'main/board.html')
