/*
 * Copyright 2021 Alex Syrnikov <pioneer19@post.cz>
 * SPDX-License-Identifier: Apache-2.0
 */

package github.karchx.wiki.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import github.karchx.wiki.databinding.ArticleFragmentBinding

@AndroidEntryPoint
class ArticleFragment : Fragment() {
    private var _binding: ArticleFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ArticleViewModel by viewModels()
    private val args: ArticleFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ArticleFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.response.observe(viewLifecycleOwner){
            binding.articlePage.loadDataWithBaseURL(
                    articleHtmlUrl(args.article,args.lang), it, "text/html", null, null)
        }
        viewModel.fetchJsonPage( articleJsonUrl(args.article,args.lang) )
    }
    private fun articleJsonUrl(article: String, lang: String ) : String {
        return "https://${lang}.wikipedia.org/w/api.php?action=parse&format=json&page=${article}&prop=text&format=json"
    }
    private fun articleHtmlUrl(article: String, lang: String ) : String {
        return "https://${lang}.wikipedia.org/wiki/${article}"
    }
}