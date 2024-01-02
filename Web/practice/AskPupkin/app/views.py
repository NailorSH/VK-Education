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
    page = request.GET.get('page', 1)
    paginator = Paginator(objects, per_page)

    return paginator.page(page)


def find_questions_by_tag(tag_name):
    return [question for question in QUESTIONS if tag_name in question['tags']]


def index(request):
    page_items = paginate(request, QUESTIONS, 10)

    return render(request, 'index.html', {'questions': page_items})


def hot(request):
    page_items = paginate(request, QUESTIONS, 10)

    return render(request, 'hot.html', {'questions': page_items})


def tag(request, tag_name):
    page_items = paginate(request, find_questions_by_tag(tag_name), 10)

    return render(request, 'tag_listing.html', {'tag': tag_name, 'questions': page_items})


def question(request, question_id):
    item = QUESTIONS[question_id]
    pagination_answers = paginate(request, item['answers'], 10)
    return render(request, 'question.html', {'question': item, 'pagination_answers': pagination_answers})


def login(request):
    return render(request, 'login.html')


def signup(request):
    return render(request, 'signup.html')


def ask(request):
    return render(request, 'ask.html')
