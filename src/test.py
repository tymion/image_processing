#!/usr/bin/python

import unittest

import algorithm

class TestFindRegion(unittest.TestCase):

    def test_simple_correct_case(self):

        data = [[1,2,3],
                [4,5,6],
                [7,8,9]]

        region = [[1,2,3],
                [4,5,6],
                [7,8,9]]

        regions = algorithm.find_region(data, 3, 3, region, 3)
        self.assertEqual(regions, [(1, 1)])

    def test_complex_correct_case(self):

        data = [[1,2,1,4,5],
                [4,5,6,7,1],
                [1,2,1,2,1],
                [8,9,4,5,6],
                [1,2,1,2,1],
                [8,9,4,5,6],
                [1,2,1,2,1]]

        region = [[1,2,1],
                [4,5,6],
                [1,2,1]]

        regions = algorithm.find_region(data, 7, 5, region, 3)
        self.assertEqual(regions, [(1,1),(3,3),(5,3)])

    def test_simple_incorrect_case(self):

        data = [[2,2,3],
                [4,5,6],
                [7,8,9]]

        region = [[1,2,3],
                [4,5,6],
                [7,8,9]]

        regions = algorithm.find_region(data, 3, 3, region, 3)
        self.assertEqual(regions, [])

    def test_simple_correct_case_with_mask(self):

        data = [[2,2,3],
                [4,5,6],
                [7,8,9]]

        region = [[-1,2,3],
                [4,5,6],
                [7,8,9]]

        regions = algorithm.find_region(data, 3, 3, region, 3)
        self.assertEqual(regions, [(1, 1)])

    def test_simple_incorrect_case_with_mask(self):

        data = [[2,1,3],
                [4,5,6],
                [7,8,9]]

        region = [[-1,2,3],
                [4,5,6],
                [7,8,9]]

        regions = algorithm.find_region(data, 3, 3, region, 3)
        self.assertEqual(regions, [])

    def test_correct_case_in_large_data(self):

        data = [[0,1,2,3],
                [0,1,2,3],
                [1,4,5,6],
                [1,7,8,9]]

        region = [[1,2,3],
                [4,5,6],
                [7,8,9]]

        regions = algorithm.find_region(data, 4, 4, region, 3)
        self.assertEqual(regions, [(2, 2)])

    def test_correct_case_in_large_data_with_mask(self):

        data = [[0,1,2,3],
                [0,1,2,3],
                [1,4,5,6],
                [1,7,8,9]]

        region = [[1,2,3],
                [4,-1,6],
                [-1,8,9]]

        regions = algorithm.find_region(data, 4, 4, region, 3)
        self.assertEqual(regions, [(2, 2)])

    def test_incorrect_case_in_large_data(self):

        data = [[0,1,2,3],
                [0,1,1,3],
                [1,4,5,6],
                [1,7,8,9]]

        region = [[1,2,3],
                [4,5,6],
                [7,8,9]]

        regions = algorithm.find_region(data, 4, 4, region, 3)
        self.assertEqual(regions, [])

    def test_incorrect_case_in_large_data_with_mask(self):

        data = [[0,1,2,3],
                [0,1,2,3],
                [1,4,5,6],
                [1,2,8,9]]

        region = [[1,2,3],
                [4,-1,6],
                [7,8,9]]

        regions = algorithm.find_region(data, 4, 4, region, 3)
        self.assertEqual(regions, [])

    def test_simple_correct_with_double_solutions(self):

        data = [[1,2,1,4,5],
                [4,5,6,7,1],
                [1,2,1,2,1],
                [8,9,4,5,6],
                [0,0,1,2,1]]

        region = [[1,2,1],
                [4,5,6],
                [1,2,1]]

        regions = algorithm.find_region(data, 5, 5, region, 3)
        self.assertEqual(regions, [(1,1),(3,3)])

    def test_simple_correct_with_all_solutions(self):

        data = [[1,1,1,1,1],
                [1,1,1,1,1],
                [1,1,1,1,1],
                [1,1,1,1,1],
                [1,1,1,1,1]]

        region = [[1,1,1],
                [1,1,1],
                [1,1,1]]

        regions = algorithm.find_region(data, 5, 5, region, 3)
        self.assertEqual(regions, [(1,1),(1,2),(1,3),(2,1),(2,2),(2,3),(3,1),
                                    (3,2),(3,3)])

class TestGetRegionFromData(unittest.TestCase):

    def test_simple_correct(self):

        data = [[1,2,1,4,5],
                [4,5,6,7,1],
                [1,2,1,2,1],
                [8,9,4,5,6],
                [0,0,1,2,1]]

        region = algorithm.get_region_from_data(data, 3, 2, 2)
        self.assertEqual(region, [[5,6,7],[2,1,2],[9,4,5]])

    def test_simple_correct_other(self):

        data = [[1,2,1,4,5],
                [4,5,6,7,1],
                [1,2,1,2,1],
                [8,9,4,5,6],
                [0,0,1,2,1]]

        region = algorithm.get_region_from_data(data, 3, 1, 3)
        self.assertEqual(region, [[1,4,5],[6,7,1],[1,2,1]])

class TestFindCommonRegions(unittest.TestCase):

    def test_simple_correct(self):

        prim_data = [[1,2,1,4,5],
                    [4,5,6,7,1],
                    [1,2,1,2,1],
                    [8,9,4,5,6],
                    [0,0,1,2,1]]

        sec_data = [[1,2,1,4,5],
                    [4,5,6,7,1],
                    [1,2,1,2,1],
                    [8,9,4,5,6],
                    [0,0,1,2,1]]

        region = algorithm.find_common_regions(prim_data, 5, 5, sec_data, 5, 5)
        self.assertEqual(region, [[(1,1),(3,3)],[(1,2)],[(1,3)],[(2,1)],[(2,2)],
                                    [(2,3)],[(3,1)],[(3,2)],[(1,1),(3,3)]])

    def test_simple_incorrect(self):

        prim_data = [[2,2,2,2,2],
                    [2,2,2,2,2],
                    [2,2,2,2,2],
                    [2,2,2,2,2],
                    [2,2,2,2,2]]

        sec_data = [[1,1,1,1,1],
                    [1,1,1,1,1],
                    [1,1,1,1,1],
                    [1,1,1,1,1],
                    [1,1,1,1,1]]

        region = algorithm.find_common_regions(prim_data, 5, 5, sec_data, 5, 5)
        self.assertEqual(region, [])

    def test_simple_correct_prim5x7(self):
        prim_data = [[1,2,1,4,5,1,2],
                    [4,5,6,7,1,1,2],
                    [1,2,1,2,1,1,2],
                    [8,9,4,5,6,1,2],
                    [0,0,1,2,1,1,2]]

        sec_data = [[1,2,1,4,5],
                    [4,5,6,7,1],
                    [1,2,1,2,1],
                    [8,9,4,5,6],
                    [0,0,1,2,1]]

        region = algorithm.find_common_regions(prim_data, 5, 7, sec_data, 5, 5)
        self.assertEqual(region, [[(1,1),(3,3)],[(1,2)],[(1,3)],[(2,1)],[(2,2)],
                                    [(2,3)],[(3,1)],[(3,2)],[(1,1),(3,3)]])

    def test_simple_correct_prim7x5(self):
        prim_data = [[1,2,1,4,5],
                    [4,5,6,7,1],
                    [1,2,1,2,1],
                    [8,9,4,5,6],
                    [0,0,1,2,1],
                    [8,9,4,5,6],
                    [0,0,1,2,1]]

        sec_data = [[1,2,1,4,5],
                    [4,5,6,7,1],
                    [1,2,1,2,1],
                    [8,9,4,5,6],
                    [0,0,1,2,1]]

        region = algorithm.find_common_regions(prim_data, 7, 5, sec_data, 5, 5)
        self.assertEqual(region, [[(1,1),(3,3)],[(1,2)],[(1,3)],[(2,1)],[(2,2)],
                                    [(2,3)],[(3,1)],[(3,2)],[(1,1),(3,3)],[(1,1),(3,3)]])

    def test_simple_correct_sec5x7(self):
        prim_data = [[1,2,1,4,5],
                    [4,5,6,7,1],
                    [1,2,1,2,1],
                    [8,9,4,5,6],
                    [0,0,1,2,1]]

        sec_data = [[1,2,1,4,5,1,2],
                    [4,5,6,7,1,1,2],
                    [1,2,1,2,1,1,2],
                    [8,9,4,5,6,1,2],
                    [0,0,1,2,1,1,2]]

        region = algorithm.find_common_regions(prim_data, 5, 5, sec_data, 5, 7)
        self.assertEqual(region, [[(1,1),(3,3)],[(1,2)],[(1,3)],[(2,1)],[(2,2)],
                                    [(2,3)],[(3,1)],[(3,2)],[(1,1),(3,3)]])

    def test_simple_correct_sec7x5(self):
        prim_data = [[1,2,1,4,5],
                    [4,5,6,7,1],
                    [1,2,1,2,1],
                    [8,9,4,5,6],
                    [1,2,1,2,1]]

        sec_data = [[1,2,1,4,5],
                    [4,5,6,7,1],
                    [1,2,1,2,1],
                    [8,9,4,5,6],
                    [1,2,1,2,1],
                    [8,9,4,5,6],
                    [1,2,1,2,1]]

        region = algorithm.find_common_regions(prim_data, 5, 5, sec_data, 7, 5)
        self.assertEqual(region, [[(1,1),(3,3),(5,3)],[(1,2)],[(1,3)],[(2,1)],[(2,2)],
                                    [(2,3)],[(3,1),(5,1)],[(3,2),(5,2)],[(1,1),(3,3),(5,3)]])

if __name__ == '__main__':
    suite = unittest.TestLoader().loadTestsFromTestCase(TestFindRegion)
    unittest.TextTestRunner(verbosity=2).run(suite)
    suite = unittest.TestLoader().loadTestsFromTestCase(TestGetRegionFromData)
    unittest.TextTestRunner(verbosity=2).run(suite)
    suite = unittest.TestLoader().loadTestsFromTestCase(TestFindCommonRegions)
    unittest.TextTestRunner(verbosity=2).run(suite)
