import math

from django.shortcuts import render
from django.core.paginator import Paginator

QUESTIONS = [
    {
        'id': i,
        'title': f'Question {i}',
        'content': f'''Lorem Ipsum is simply dummy text of the printing and typesetting
            industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s,
            when an unknown printer took a galley of type and scrambled it to make a type specimen
            book.''',
        'tags': ['python', 'programming', 'askme'],
        'answers': [
            {
                'id': i,
                'content': f'''First of all I would like to thank you for the invitation to participate in such a
                Russia is the huge territory which in many respects needs to be render habitable. {i}'''
            } for i in range(50)
        ]
    } for i in range(100)
]


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


def find_questions_by_tag(tag_name):
    return [question for question in QUESTIONS if tag_name in question['tags']]


def index(request):
    page_items, pagination = paginate(request, QUESTIONS, 5)

    return render(
        request,
        'index.html',
        {
            'questions': page_items,
            'pages': pagination
        }
    )


def hot(request):
    page_items, pagination = paginate(request, QUESTIONS, 10)

    return render(request, 'hot.html', {
        'questions': page_items,
        'pages': pagination
    })


def tag(request, tag_name):
    page_items, pagination = paginate(request, find_questions_by_tag(tag_name), 10)

    return render(request, 'tag_listing.html', {
        'tag': tag_name,
        'questions': page_items,
        'pages': pagination
    })


def question(request, question_id):
    item = QUESTIONS[question_id]
    answers, pagination = paginate(request, item['answers'], 10)

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
