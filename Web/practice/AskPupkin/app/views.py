import math

from django.shortcuts import render, get_object_or_404
from django.core.paginator import Paginator

from app.models import *


def paginate(request, objects, per_page=15):
    result = None
    first = end = 1
    num_page = 1
    pages_pagination = []
    try:
        page = request.GET.get('page', 1)

        paginator = Paginator(objects, per_page)
        result = paginator.page(page)

        num_page = int(page)
    except Exception as e:
        print(e)

    all_pages = math.ceil(len(objects) / per_page)

    i = num_page
    count = 0
    if i < all_pages:
        while count < 3:
            pages_pagination.append(i)
            i += 1
            if i == all_pages:
                break
            count += 1

    if num_page == 1:
        first = None
    if num_page == all_pages:
        end = None

    return (result, {
        "first": first,
        "end": end,
        "enditem": all_pages,
        "items": pages_pagination
    })


def index(request):
    questions = Question.objects.new()
    page_items, pagination = paginate(request, questions, 10)

    return render(
        request,
        'index.html',
        {
            'questions': page_items,
            'pages': pagination
        }
    )


def hot(request):
    hot_questions = Question.objects.hot()
    page_items, pagination = paginate(request, hot_questions, 10)

    return render(request, 'hot.html', {
        'questions': page_items,
        'pages': pagination
    })


def tag(request, tag_name):
    tag_questions = Tag.objects.get(tag_name=tag_name).questions.all()
    page_items, pagination = paginate(request, tag_questions, 10)

    return render(request, 'tag_listing.html', {
        'tag': tag_name,
        'questions': page_items,
        'pages': pagination
    })


def question(request, question_id):
    item = get_object_or_404(Question, id=question_id)

    answers, pagination = paginate(request, item.answers, 10)

    return render(request, 'question.html', {
        'question': item,
        'pagination_answers': answers,
        'pages': pagination

    })


def login(request):
    return render(request, 'login.html')


def signup(request):
    return render(request, 'signup.html')


def ask(request):
    return render(request, 'ask.html')


def settings(request):
    return render(request, 'settings.html')
